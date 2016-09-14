package com.rsqn.common.util;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.*;

/**
 * Created by mandrewes on 25/06/14.
 */
public abstract class AbstractJSAggregationAction extends AbstractAction {
    protected Logger log = LoggerFactory.getLogger(getClass());
    private String profiles;
    private static final Map<String, String> cache = new Hashtable<String, String>();

    public String getProfiles() {
        return profiles;
    }

    public void setProfiles(String profiles) {
        this.profiles = profiles;
    }

    private String generateCacheKey(List profiles) {
        return "cached-" + profiles;
    }

    protected File resolveFile(File cwd, String relativePath) throws IOException {
        File f = new File(cwd,relativePath);
        return f;
    }

    protected String getFileContents(File f) throws IOException {
        return IOUtils.toString(new FileInputStream(f));
    }

    protected abstract File getTopLevelDirectory();

    protected abstract Map<String,String> getTagsToReplace();

    private String resolveRequestedPath() {
        String requestedUri = this.getContext().getRequest().getRequestURI();
        String contextPath = this.getContext().getServletContext().getContextPath();

        if ( requestedUri.startsWith(contextPath)) {
            requestedUri = requestedUri.substring(contextPath.length());
        }
        log.info("requested Uri is " + requestedUri);
        return requestedUri;
    }

    @DefaultHandler
    public Resolution view() {
        String contents = "fail";

        try {
            List<String> profileList = new ArrayList<String>();

            if (profiles != null) {
                String[] profileSplit = profiles.split(",");
                Collections.addAll(profileList, profileSplit);
            }

            if (profileList.contains("clearcache")) {
                cache.clear();
            }

            contents = cache.get(generateCacheKey(profileList));
            if (contents == null || profileList.contains("nocache")) {
                log.info("JS Profiles = " + profileList);
                StringBuffer buffer = new StringBuffer();
                aggregateFromFile(buffer, getTopLevelDirectory(), resolveRequestedPath(), profileList);
                contents = buffer.toString();

                Map<String,String> tags = getTagsToReplace();
                for (String key : tags.keySet() ) {
                    String tagValue = tags.get(key);
                    log.info("replacing #" + key + "# = " + tagValue);
                    contents = contents.replaceAll("#" + key + "#", tagValue);
                }

                if (!profileList.contains("nocompile")) {
                    JavaScriptMinifier minifier = new JavaScriptMinifier();
                    contents = minifier.minify(contents, profileList.contains("advanced"));
                }
                cache.put(generateCacheKey(profileList), contents);
            }

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
            contents = e.getMessage();
        }

        return new StreamingResolution("application/javascript", contents);
    }

    private String loadTemplate(String path) throws IOException {
        log.info("loading template " + path);
        ClassPathResource resource = new ClassPathResource(path);

        String s = IOUtils.toString(resource.getInputStream());

        s = s.replaceAll("\\r","");
        s = s.replaceAll("\\n","");
        s = s.replaceAll("\\t","");

//        s = StringEscapeUtils.escapeJava(s);
        s = StringEscapeUtils.escapeEcmaScript(s);
        return s;
    }

    private String parseTemplateToken(String line) {
        int start = line.indexOf("#template");
        int end = line.indexOf("#",start+1);
        return line.substring(start, end+1);
    }

    private String parseTemplateInclude(String line) {
        String path = line.split("=")[1];
        path = path.replace('#',' ').trim();
        return path;
    }

    protected void aggregateFromFile(StringBuffer buffer, File cwd, String fileName, List<String> profiles) throws IOException {
        String includeToken = "#include";
        String templateToken = "#template"; // #template=filePathInClassPath id document.write('#template=filePath');
        String ifProfileToken = "#ifprofile";
        String ifNotProfileToken = "#ifnot_profile";
        String endIfToken = "#endif";
        File f = resolveFile(cwd,fileName);
        File nCwd = f.getParentFile();
        String baseContents = getFileContents(f);
        BufferedReader reader = new BufferedReader(new StringReader(baseContents));
        String line;
        String includeFileName;

        final int DEFAULT_STATE = 0;
        final int IN_PROFILE_STATE_IGNORE = 1;
        final int IN_PROFILE_STATE_PROCESS = 2;
        int state = DEFAULT_STATE;

        while ( (line = reader.readLine()) != null) {
            if ( state == IN_PROFILE_STATE_IGNORE || state == IN_PROFILE_STATE_PROCESS ) {
                if ( line.trim().startsWith(endIfToken)) {
                    state = DEFAULT_STATE;
                    continue;
                }
            }
            if ( state == IN_PROFILE_STATE_IGNORE) {
                continue;
            }

            if ( line.trim().startsWith(includeToken)) {
                includeFileName = line.replace(includeToken,"").trim();
                buffer.append("\n\n\n");
                buffer.append("// " + includeFileName);
                buffer.append("\n\n");
                aggregateFromFile(buffer, nCwd, includeFileName, profiles);
            } else if ( line.trim().contains("#template")) {
                String templateInclude = parseTemplateToken(line);
                log.info("Template include " + templateInclude);
                String templateContent = loadTemplate(parseTemplateInclude(templateInclude));
                line = line.replace(templateInclude,templateContent);
                buffer.append("// " + templateInclude);
                buffer.append("\n\n");
                buffer.append(line);
                buffer.append("\n\n");
            } else if (line.trim().startsWith(ifProfileToken)) {
                String profileName = line.replace(ifProfileToken,"").trim();
                log.debug("IfProfile [" + profileName + "]");
                if( profiles.contains(profileName)) {
                    state = IN_PROFILE_STATE_PROCESS;
                    log.debug("IfProfile process [" + profileName + "]");
                } else {
                    log.debug("IfProfile ignore [" + profileName + "]");
                    state = IN_PROFILE_STATE_IGNORE;
                }
            } else if (line.trim().startsWith(ifNotProfileToken)) {
                String profileName = line.replace(ifNotProfileToken,"").trim();
                log.debug("IfNotProfile [" + profileName + "]");
                if( profiles.contains(profileName)) {
                    log.debug("IfNotProfile ignoring [" + profileName + "]");
                    state = IN_PROFILE_STATE_IGNORE;
                } else {
                    state = IN_PROFILE_STATE_PROCESS;
                    log.debug("IfNotProfile processing [" + profileName + "]");
                }
            } else {
                buffer.append(line);
                buffer.append("\n");
            }
        }
    }
}


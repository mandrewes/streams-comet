package com.rsqn.common;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.servlet.ServletException;
import java.io.File;

public class EmbeddedJetty {
    private static final Logger log = LoggerFactory.getLogger(EmbeddedJetty.class);
    private String appBaseDir;
    private int port;
    private String contextPath;

    @Required
    public void setAppBaseDir(String appBaseDir) {
        this.appBaseDir = appBaseDir;
    }

    @Required
    public void setPort(int port) {
        this.port = port;
    }

    @Required
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void start() throws ServletException {
        try {
            log.info("Starting EmbeddedJetty on port {}", port);
            log.info("Current Working Directory is ", new File("").getAbsolutePath());
            Server server = new Server(port);
            WebAppContext context = new WebAppContext();
            File webXml = null;

            String[] webXmlPaths = {appBaseDir + "/WEB-INF/web.xml"};

            for (String webXmlPath : webXmlPaths) {
                log.info("Looking for webXml at ({})", webXmlPath);
                webXml = new File(webXmlPath);
                if (webXml.exists()) {
                    log.info("Found webXML at ({})", webXml.getAbsolutePath());
                    break;
                } else {
                    log.info("No webXML at ({})", webXmlPath);
                    webXml = null;
                }
            }

            if (webXml == null) {
                log.error("No webXML Found - exiting");
                return;
            }

            File webDirectory = null;
            String[] webDirectoryPaths = {appBaseDir};
            for (String webDirPath : webDirectoryPaths) {
                log.info("Looking for webDir at ({})", webDirPath);
                webDirectory = new File(webDirPath);
                if (webXml.exists()) {
                    log.info("Found webDir at ({})", webDirectory.getAbsolutePath());
                    break;
                } else {
                    log.info("No webDir at ({})", webDirPath);
                    webDirectory = null;
                }
            }

            if (webDirectory == null) {
                log.error("No webDir Found - exiting");
                return;
            }

            context.setDescriptor(webXml.getPath());
            context.setResourceBase(webDirectory.getPath());
            context.setContextPath(contextPath);
            context.setParentLoaderPriority(true);

            server.setHandler(context);
            WebSocketServerContainerInitializer.configureContext(context);
            server.start();
            server.join();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
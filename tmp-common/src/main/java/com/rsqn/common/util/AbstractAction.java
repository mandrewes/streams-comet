package com.rsqn.common.util;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AbstractAction implements ActionBean {
    protected Logger log = LoggerFactory.getLogger(getClass());
    private ActionBeanContext context;

    public void setContext(ActionBeanContext actionBeanContext) {
        context = actionBeanContext;
    }

    public ActionBeanContext getContext() {
        return context;
    }

    protected String resolvePathWithoutContext() {
        String servicePath = getContext().getRequest().getRequestURI();
        String contextPath = getContext().getServletContext().getContextPath();
        servicePath = servicePath.replace(contextPath, "");

        return servicePath;
    }

    public File getRealPath(String path) {
        return new File(this.getContext().getServletContext().getRealPath(path));
    }

}
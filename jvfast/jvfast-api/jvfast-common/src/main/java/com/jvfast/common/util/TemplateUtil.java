package com.jvfast.common.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

public class TemplateUtil {

    /**
     * 模板转换
     *
     * @param template
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processTemplate(Template template, Map<String, Object> model) throws IOException, TemplateException {
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
}

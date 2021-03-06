package com.rbittencourt.aws.cost.miner.infrastructure.file;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

@Component
public class TemplateWriter {

    @Autowired
    private VelocityEngine engine;

    public String write(String templateName, Map<String, Object> values) {
        Template template = engine.getTemplate(templateName);

        VelocityContext context = new VelocityContext();
        context.put("StringUtils", StringUtils.class);

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }

}

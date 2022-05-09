package ru.artem.perfsystem.resource.template;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@CheckedTemplate
public class DefaultTemplate {

    public static native TemplateInstance mainPage();

}

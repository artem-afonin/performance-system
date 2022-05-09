package ru.artem.perfsystem.resource.template;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import ru.artem.perfsystem.resource.page.wrapper.VariationAnalyticsWrapper;

import java.util.List;

@CheckedTemplate
public class AnalyticsTemplate {

    public static native TemplateInstance analyticsPanel();

    public static native TemplateInstance variationAnalyticsPanel(List<VariationAnalyticsWrapper> wrapperList);

}

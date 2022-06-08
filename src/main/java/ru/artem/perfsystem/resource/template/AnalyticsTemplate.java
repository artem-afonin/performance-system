package ru.artem.perfsystem.resource.template;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import ru.artem.perfsystem.resource.page.wrapper.ComparisonAnalyticsWrapper;
import ru.artem.perfsystem.resource.page.wrapper.VariationAnalyticsWrapper;

import java.util.List;

@CheckedTemplate(requireTypeSafeExpressions = false)
public class AnalyticsTemplate {

    public static native TemplateInstance analyticsPanel();

    public static native TemplateInstance variationAnalyticsPanel(List<VariationAnalyticsWrapper> wrapperList);

    public static native TemplateInstance comparisonAnalyticsPanel(ComparisonAnalyticsWrapper wrapper);

}

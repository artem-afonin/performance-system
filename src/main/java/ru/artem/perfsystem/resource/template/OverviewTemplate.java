package ru.artem.perfsystem.resource.template;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import ru.artem.perfsystem.resource.page.wrapper.OverviewPanelWrapper;

@CheckedTemplate
public class OverviewTemplate {

    public static native TemplateInstance overviewPanel(OverviewPanelWrapper wrapper);

}

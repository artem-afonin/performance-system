package ru.artem.perfsystem;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import ru.artem.perfsystem.entity.dto.Report;

import java.util.List;

@CheckedTemplate
public class LogTemplate {

    public static native TemplateInstance logUpload();

    public static native TemplateInstance logUploadConfirm(List<Report> reportList);

}

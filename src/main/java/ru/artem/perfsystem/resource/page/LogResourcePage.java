package ru.artem.perfsystem.resource.page;

import io.quarkus.qute.TemplateInstance;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import ru.artem.perfsystem.LogTemplate;
import ru.artem.perfsystem.entity.dto.Report;
import ru.artem.perfsystem.util.FileUtil;
import ru.artem.perfsystem.util.LogParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("/log")
public class LogResourcePage {

    @GET
    @Path("/upload")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return LogTemplate.logUpload();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance upload(MultipartFormDataInput formData) throws IOException {
        List<String> fileContent = FileUtil.streamToStringList(formData.getFormDataPart("logFile", InputStream.class, null));
        List<Report> reportList = new LogParser().parse(fileContent);
        return LogTemplate.logUploadConfirm(reportList);
//        return reportList.stream().map(Report::toString).collect(Collectors.joining("\n"));
//        return String.join("\n", fileContent);
    }

}

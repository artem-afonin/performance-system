package ru.artem.perfsystem.resource.page;

import com.opencsv.exceptions.CsvException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.qute.TemplateInstance;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import ru.artem.perfsystem.resource.template.LogTemplate;
import ru.artem.perfsystem.entity.dto.Report;
import ru.artem.perfsystem.util.FileUtil;
import ru.artem.perfsystem.util.LogParser;

import javax.transaction.Transactional;
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
    @Transactional
    public TemplateInstance upload(MultipartFormDataInput formData) throws IOException, CsvException {
        String benchmarkName = formData.getFormDataPart("benchmark", String.class, null);
        String hostName = formData.getFormDataPart("host", String.class, null);
        String jdkName = formData.getFormDataPart("jdkName", String.class, null);
        String jdkVersionString = formData.getFormDataPart("jdkVersion", String.class, null);
        Integer jdkVersion = Integer.valueOf(jdkVersionString);
        List<String> fileContent = FileUtil.streamToStringList(formData.getFormDataPart("logFile", InputStream.class, null));
        List<Report> reportList = new LogParser().parse(fileContent, benchmarkName, hostName, jdkName, jdkVersion);
        reportList.forEach(PanacheEntityBase::persistAndFlush);
        return LogTemplate.logUpload();
    }

}

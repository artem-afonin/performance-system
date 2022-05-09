package ru.artem.perfsystem.resource.page;

import io.quarkus.qute.TemplateInstance;
import ru.artem.perfsystem.resource.template.DefaultTemplate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class MainResourcePage {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return DefaultTemplate.mainPage();
    }

}

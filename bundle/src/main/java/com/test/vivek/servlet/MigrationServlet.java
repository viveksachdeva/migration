package com.test.vivek.servlet;

import com.test.vivek.model.Person;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import org.jcrom.Jcrom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.io.IOException;

@SlingServlet(paths = {"/bin/migration"}, methods = {"GET"}, generateComponent = false, extensions = {"json"}, generateService = true)
@Component(description = "Uploads content to CRX from JSON", enabled = true, immediate = true, metatype = true)
public class MigrationServlet extends SlingAllMethodsServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationServlet.class);

    @Reference
    SlingRepository repository;

    protected Session session;

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Person person = new Person("Test User", 30);
        Jcrom jcrom = new Jcrom();
        try {

            LOGGER.info("Inside Migration Servlet::::::Session" + jcrom.map(Person.class));
            jcrom.map(Person.class);
            session = repository.loginAdministrative(null);
            LOGGER.info("Repo logged in:::::::::::::::::::");
            Node parentNode = session.getRootNode();
            LOGGER.info("Parent node" + parentNode);
            jcrom.addNode(parentNode, person);
            session.save();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        } finally {
            session.logout();
        }
        response.getWriter().write("Compleytye ");

    }
}

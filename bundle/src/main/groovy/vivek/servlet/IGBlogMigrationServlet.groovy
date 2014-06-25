package com.test.vivek.servlet

import com.test.vivek.componentmodel.*
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.sling.SlingServlet
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.SlingAllMethodsServlet
import org.apache.sling.jcr.api.SlingRepository
import org.jcrom.Jcrom
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.jcr.Session

@SlingServlet(
        paths=["/bin/jcrtest"]
)
@org.apache.felix.scr.annotations.Properties([
@Property(name = "service.vendor", value = "Time Warner Cable"),
@Property(name = "service.description", value = "JCR Repositories Test Servlet")
])
class IGBlogMigrationServlet extends SlingAllMethodsServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(IGBlogMigrationServlet.class);

    @org.apache.felix.scr.annotations.Reference
    SlingRepository repository;

    protected Session session;

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Jcrom jcrom = new Jcrom();
        def rawFeed = new URL("http://www.intelligrape.com/blog/author/vivek.sachdeva/feed").text
        LOGGER.info("--------------raw feed------${rawFeed}")
        try {
            javax.jcr.Node parentNode = session.getRootNode();
            session = repository.loginAdministrative(null);
            jcrom.map(BlogComponentModel.class)
            def records = new XmlParser().parseText(rawFeed)?.channel?.item
            records.each {
                String blogTitle = it?.title?.text()
                String blogDescription = it?.description?.text()
                TextFieldComponentModel textFieldComponentModel = new TextFieldComponentModel(text: blogDescription)
                TitleComponentModel titleComponentModel = new TitleComponentModel(text: blogTitle)
                BlogComponentModel blogComponentModel = new BlogComponentModel(name: "blog", textcomp: textFieldComponentModel, title: titleComponentModel)
                jcrom.addNode(parentNode, blogComponentModel);
            }
            session.save();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        } finally {
            session.logout();
        }
        response.getWriter().write("Compleytye ");

    }
}
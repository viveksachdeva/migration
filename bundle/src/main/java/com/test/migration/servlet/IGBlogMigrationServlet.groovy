package com.test.migration.servlet

import com.test.migration.model.*
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service
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
        paths= ["/bin/migrate" ],
        methods=[ "GET" ],
        generateComponent = false
)
@Service
@Component
class IGBlogMigrationServlet extends SlingAllMethodsServlet{
    private static final Logger LOGGER = LoggerFactory.getLogger(IGBlogMigrationServlet.class);

    @org.apache.felix.scr.annotations.Reference
    SlingRepository repository;

    protected Session session;

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Jcrom jcrom = new Jcrom();
        def rawFeed = new URL("http://www.intelligrape.com/blog/author/vivek.sachdeva/feed").text
        try {
            session = repository.loginAdministrative(null);
            javax.jcr.Node parentNode = session.getRootNode();
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
            session?.save();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        } finally {
            session?.logout();
        }
        response.getWriter().write("Completed ");

    }
}
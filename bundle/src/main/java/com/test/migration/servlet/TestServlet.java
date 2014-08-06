package com.test.migration.servlet;

import com.test.migration.ParseXML;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SlingServlet(paths = {"/bin/doit"})
public class TestServlet extends SlingAllMethodsServlet{
    private static final Logger log = LoggerFactory.getLogger(TestServlet.class);

    @Reference
    ParseXML parseXML;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String url = "http://www.intelligrape.com/blog/author/vivek.sachdeva/feed";

        HttpClient client = new DefaultHttpClient();
        HttpGet req = new HttpGet(url);

        HttpResponse res = client.execute(req);

        log.info("\nSending 'GET' request to URL : " + url);
        log.info("Response Code : " + res.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(res.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        parseXML.convertXMLtoJCRContent(result.toString());


    }
}

package com.test.migration.impl;

import com.test.migration.ParseXML;
import com.test.migration.model.BlogComponentModel;
import com.test.migration.model.TextFieldComponentModel;
import com.test.migration.model.TitleComponentModel;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.jcrom.Jcrom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.jcr.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, metatype = true, label = "HttpClientFactory Service", description = "Creates an HttpClient")
@Service(ParseXML.class)
public class ParseXMLImpl implements ParseXML {

    @Reference
    SlingRepository repository;

    protected Session session;
    private static final Logger log = LoggerFactory.getLogger(ParseXMLImpl.class);

    @Override
    public void convertXMLtoJCRContent(String rawFeed) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(rawFeed));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("item");

            for (int i = 0; i < nodes.getLength(); i++) {
                List<String> list = new ArrayList<String>();
                Element element = (Element) nodes.item(i);

                NodeList title = element.getElementsByTagName("title");
                Element line = (Element) title.item(0);
                log.info("Title::: " + getCharacterDataFromElement(line));
                list.add(getCharacterDataFromElement(line));
                NodeList description = element.getElementsByTagName("description");
                line = (Element) description.item(0);
                log.info("description: " + getCharacterDataFromElement(line));
                list.add(getCharacterDataFromElement(line));
                createNodeInJCR(list);
            }
        } catch (Exception e) {

        }
    }

    private void createNodeInJCR(List<String> list) {
        Jcrom jcrom = new Jcrom();
        try {
            javax.jcr.Node parentNode = session.getRootNode();
            session = repository.loginAdministrative(null);
            jcrom.map(BlogComponentModel.class);
            String blogTitle = list.get(0);
            String blogDescription = list.get(1);
            TextFieldComponentModel textFieldComponentModel = new TextFieldComponentModel();
            textFieldComponentModel.setText(blogDescription);
            TitleComponentModel titleComponentModel = new TitleComponentModel();
            titleComponentModel.setText(blogTitle);
            BlogComponentModel blogComponentModel = new BlogComponentModel();
            blogComponentModel.setName("blog");
            blogComponentModel.setTextcomp(textFieldComponentModel);
            blogComponentModel.setTitle(titleComponentModel);
            jcrom.addNode(parentNode, blogComponentModel);
            session.save();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        } finally {
            session.logout();
        }
    }

    private String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}

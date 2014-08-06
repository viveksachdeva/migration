package com.test.migration.model

import org.jcrom.annotations.JcrChildNode
import org.jcrom.annotations.JcrName
import org.jcrom.annotations.JcrPath
import org.jcrom.annotations.JcrProperty

class BlogComponentModel {
    @JcrName String name;
    @JcrPath String path;
    @JcrProperty(name="sling:resourceType") String resourceType = "migration/components/content/blog";
    @JcrChildNode(createContainerNode=false) TextFieldComponentModel textcomp
    @JcrChildNode(createContainerNode=false) TitleComponentModel title

}

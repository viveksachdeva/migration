package com.test.migration.model

import org.jcrom.annotations.JcrName
import org.jcrom.annotations.JcrPath
import org.jcrom.annotations.JcrProperty

class TitleComponentModel {
    @JcrName String name = "title"
    @JcrPath String path;
    @JcrProperty(name="sling:resourceType") String resourceType = "foundation/components/title";
    @JcrProperty String text
}

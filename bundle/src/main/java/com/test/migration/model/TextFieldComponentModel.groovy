package com.test.migration.model

import org.jcrom.annotations.JcrName
import org.jcrom.annotations.JcrPath
import org.jcrom.annotations.JcrProperty

class TextFieldComponentModel {
    @JcrName String name = "textcomp"
    @JcrPath String path;
    @JcrProperty(name="sling:resourceType") String resourceType = "foundation/components/text";
    @JcrProperty String text
    @JcrProperty String textIsRich = true
}

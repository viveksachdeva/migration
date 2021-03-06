package com.test.vivek.model

import org.jcrom.annotations.JcrName
import org.jcrom.annotations.JcrPath
import org.jcrom.annotations.JcrProperty

class ImageComponentModel {
    @JcrName String name
    @JcrPath String path
    @JcrProperty(name="sling:resourceType") String resourceType = "foundation/components/image";
    @JcrProperty String fileReference
}

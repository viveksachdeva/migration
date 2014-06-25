package com.test.vivek.componentmodel

import org.jcrom.annotations.JcrChildNode
import org.jcrom.annotations.JcrName
import org.jcrom.annotations.JcrPath
import org.jcrom.annotations.JcrProperty

class BlogComponentModel {
    @JcrName String name;
    @JcrPath String path;
    @JcrProperty(name="sling:resourceType") String resourceType = "test/migration/blog";
    @JcrChildNode TextFieldComponentModel textcomp
    @JcrChildNode com.test.vivek.componentmodel.TitleComponentModel title

    public static void main(String[] args){
        println("::::::::::::::::::::::::sdaaaaaaaaaaaaaaaaa:")
    }

}

package com.Vinctor.bean;


import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by Vinctor on 2017/3/10.
 */

public class FieldEntity {
    String className;
    String feildName;
    private Map<Class<? extends Annotation>, Annotation> annotataionMap = new HashMap<>();

    public FieldEntity(Elements elementUtil, VariableElement element) {
        this.className = element.getEnclosingElement().getSimpleName().toString();
        this.feildName = element.getSimpleName().toString();
    }

    public String getClassName() {
        return className;
    }

    public String getFeildName() {
        return feildName;
    }


    @Override
    public String toString() {
        return "feildName--" + feildName;
    }

}

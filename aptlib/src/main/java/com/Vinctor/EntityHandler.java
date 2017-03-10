package com.Vinctor;

import com.Vinctor.bean.ClassEntity;
import com.Vinctor.bean.FieldEntity;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by Vinctor on 2017/3/10.
 */

public class EntityHandler {
    //    ProcessingEnvironment env;
    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;
    private Messager messager;
    private Map<String, ClassEntity> classEntityMap;
    private String GENERATE_TAG = "_VV_";

    public EntityHandler(ProcessingEnvironment processingEnv) {
//        this.env = processingEnv;
        this.elementUtils = processingEnv.getElementUtils();
        this.typeUtils = processingEnv.getTypeUtils();
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
        classEntityMap = new HashMap<>();
    }

    public Map<String, ClassEntity> handlerElement(RoundEnvironment env, Vprocessor processor) {
        for (Class<? extends Annotation> support : processor.getSupportedAnnotations()) {
            for (Element element : env.getElementsAnnotatedWith(support)) {
                if (element.getKind() == ElementKind.FIELD) handlerField((VariableElement) element);

            }
        }
        return classEntityMap;
    }

    /**
     * handler field annotaions
     *
     * @param element
     */
    private void handlerField(VariableElement element) {
        //buider bean
        FieldEntity fieldEntity = new FieldEntity(elementUtils, element);
        printNormalMsg(fieldEntity.toString());
        //add to map
        //get the class name
        String clasaName = fieldEntity.getClassName();
        //builder class entity according the classname
        if (classEntityMap.get(clasaName) == null) {
            classEntityMap.put(clasaName,
                    new ClassEntity(elementUtils, typeUtils,
                            (TypeElement) element.getEnclosingElement()));
        }
        //set the field
        ClassEntity classEntity = classEntityMap.get(clasaName);
        classEntity.addFieldEntity(fieldEntity);
        log("classEntity", classEntity.toString());
    }


    protected void printNormalMsg(String msg, Element element) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg, element);
    }

    protected void printNormalMsg(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    protected void log(String tag, String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, tag + "--->" + msg);
    }

    protected void printErrorMsg(String msg, Element element) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
    }

    protected void printWranningMsg(String msg, Element element) {
        messager.printMessage(Diagnostic.Kind.WARNING, msg, element);
    }
}

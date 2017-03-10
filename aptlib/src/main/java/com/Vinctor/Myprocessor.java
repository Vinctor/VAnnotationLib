package com.Vinctor;

import com.Vinctor.bean.ClassEntity;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by Vinctor on 2017/3/10.
 */
@AutoService(Processor.class)
public class Myprocessor extends Vprocessor {
    @Override
    protected Class<? extends Annotation>[] getSupportedAnnotations() {
        return new Class[]{VBind.class};
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //build
        Map<String, ClassEntity> map = entityHandler.handlerElement(roundEnv, this);
        //
        for (Map.Entry<String, ClassEntity> item : map.entrySet()) {

        }
        return false;
    }
}

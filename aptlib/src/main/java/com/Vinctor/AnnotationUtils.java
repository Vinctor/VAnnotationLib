package com.Vinctor;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * Created by Vinctor on 2017/3/20.
 */

public class AnnotationUtils {
    public static String getAnnotationTypeCanonicalName(Annotation annotation) {
        try {
            Class<?> clazz = annotation.annotationType();
            return clazz.getCanonicalName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            return classTypeElement.getQualifiedName().toString();
        }
    }

    public static String getAnnotationTypeSimpleName(Annotation annotation) {
        try {
            Class<?> clazz = annotation.annotationType();
            return clazz.getSimpleName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            return classTypeElement.getSimpleName().toString();
        }
    }


    public static <T extends Annotation> String getAnnotationTypeCanonicalName(Element element, Class<T> annotationClazz) {
        T annotation = element.getAnnotation(annotationClazz);
        try {
            Class<?> clazz = annotation.annotationType();
            return clazz.getCanonicalName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            return classTypeElement.getQualifiedName().toString();
        }
    }
}

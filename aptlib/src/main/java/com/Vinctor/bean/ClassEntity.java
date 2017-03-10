package com.Vinctor.bean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by Vinctor on 2017/3/10.
 */

public class ClassEntity {
    private WeakReference<TypeElement> elementWeakCache;
    private Name classSimpleName;
    private String classPackage;
    private Set<Modifier> modifierSet;
    private String className;
    private String superclass;
    private List<String> interfaces = new ArrayList<>();
    private Map<String, FieldEntity> fields = new HashMap<>();

    /**
     * @param elementUtils
     * @param typeUtils
     * @param element      current anntated class
     */
    public ClassEntity(Elements elementUtils, Types typeUtils, TypeElement element) {
        elementWeakCache = new WeakReference<TypeElement>(element);
        this.classPackage = elementUtils.getPackageOf(element).getQualifiedName().toString();
        this.modifierSet = element.getModifiers();
        this.className = element.toString();
        this.classSimpleName = element.getSimpleName();
        if ("java.lang.Object".equals(element.getSuperclass().toString()))
            this.superclass = null;
        else
            this.superclass = element.getSuperclass().toString();
        List<? extends TypeMirror> interfaces = element.getInterfaces();

        for (TypeMirror anInterface : interfaces)
            this.interfaces.add(typeUtils.asElement(anInterface).toString());
    }

    public void addFieldEntity(FieldEntity fieldEntity) {
        String fieldName = fieldEntity.getFeildName();
        if (fields.get(fieldName) == null) {
            fields.put(fieldName, fieldEntity);
        }
    }


    @Override
    public String toString() {
        StringBuilder fieldString = new StringBuilder();
        StringBuilder methodString = new StringBuilder();
        StringBuilder interfacesString = new StringBuilder();
        StringBuilder annotataionMapString = new StringBuilder();
        for (Map.Entry<String, FieldEntity> item : fields.entrySet()) {
            fieldString.append("FieldKey:" + item.getKey() +
                    "\tFieldValue:\n" + item.getValue().toString() + "\n");
        }
//        for (Map.Entry<String, MethodEntity> item : methods.entrySet()) {
//            methodString.append("Methodkey:"+ item.getKey()+
//                    "\tMethodValue:\n"+ item.getValue().toString()+"\n");
//        }
        for (int i = 0; i < interfaces.size(); i++) {
            interfacesString.append("interfaces__index:" + i + ":" + interfaces.get(i));
        }
        StringBuilder result = new StringBuilder();
        result.append("{\n" +
                "classPackage:" + classPackage + "\n" +
                "modifierSet:" + modifierSet + "\n" +
                "className:" + className + "\n" +
                "classSimpleName:" + classSimpleName + "\n" +
                "superclass:" + superclass + "\n" +
                fieldString.toString() + "\n" +
                methodString.toString() + "\n" +
                annotataionMapString.toString() + "\n" +
                interfacesString.toString() + "\n" +
                "}");
        return result.toString();
    }
}

package com.example;

import com.Vinctor.AnnotationUtils;
import com.Vinctor.OnRegulaListener;
import com.Vinctor.VBind;
import com.Vinctor.VLog;
import com.Vinctor.VOnClick;
import com.Vinctor.Vprocessor;
import com.Vinctor.bean.ClassEntity;
import com.Vinctor.bean.FieldEntity;
import com.Vinctor.bean.MethodEntity;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Created by Vinctor on 2017/3/10.
 */
@AutoService(Processor.class)
public class Myprocessor extends Vprocessor {
    public String GENERATE_TAG = "_VV_binder";

    @Override
    protected Class<? extends Annotation>[] getSupportedAnnotations() {
        return new Class[]{VBind.class, VOnClick.class};
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        VLog.setDebug(true);
        entityHandler.setOnFieldRegulaListener(new OnRegulaListener<VariableElement>() {
            @Override
            public String onRegula(VariableElement element) {
                if (element.getModifiers().contains(Modifier.PRIVATE))
                    return "the field " + element.getSimpleName() + " cannot be private";
                return "";
            }
        });
        entityHandler.setOnMethodRegulaListener(new OnRegulaListener<ExecutableElement>() {
            @Override
            public String onRegula(ExecutableElement element) {
                if (element.getModifiers().contains(Modifier.PRIVATE))
                    return "the method " + element.getSimpleName() + " cannot be private";
                return "";
            }
        });
        //build
        Map<String, ClassEntity> map = entityHandler.handlerElement(roundEnv, this);
        //
        for (Map.Entry<String, ClassEntity> item : map.entrySet()) {
            entityHandler.generateCode(brewJava(item));
        }
        return false;
    }

    private JavaFile brewJava(Map.Entry<String, ClassEntity> item) {
        ClassEntity classEntity = item.getValue();
        String packageName = classEntity.getClassPackageName();
        String className = classEntity.getClassSimpleName();


        CodeBlock.Builder builder = CodeBlock.builder();
        Map<String, FieldEntity> fields = classEntity.getFields();
        for (Map.Entry<String, FieldEntity> field : fields.entrySet()) {
            FieldEntity fieldEntity = field.getValue();
            VariableElement element = fieldEntity.getElement();
            String fieldString = fieldEntity.getFeildName();
            int id = element.getAnnotation(VBind.class).value();
            TypeName classtype = ClassName.get(fieldEntity.getTypeMirror());
            builder.add("target.$L=($T)target.findViewById($L);\n", fieldString, classtype, id);
        }
        Map<String, MethodEntity> methods = classEntity.getMethods();
        for (Map.Entry<String, MethodEntity> method : methods.entrySet()) {
            MethodEntity methodEntity = method.getValue();
            String methodName = methodEntity.getMethodName();
            int[] ids = methodEntity.getMethodElement().getAnnotation(VOnClick.class).value();
            ClassName hoverboard = ClassName.get("android.view", "View");
            for (int id : ids) {
                builder.add("target.findViewById($L)\n" +
                        "      .setOnClickListener(new $T.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick($T v) {\n" +
                        "                target.$L();\n" +
                        "            }\n" +
                        "        });\n", id, hoverboard, hoverboard, methodName);
            }
        }
        MethodSpec methodSpec =
                MethodSpec.methodBuilder("bind")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(TypeName.get(classEntity.getElement().asType()), "target", Modifier.FINAL)
                        .addCode(builder.build())
                        .build();

        TypeSpec typeSpec = TypeSpec
                .classBuilder(className + GENERATE_TAG)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();


        JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                .build();

        entityHandler.printNormalMsg(javaFile.toString());
        return javaFile;
    }
}

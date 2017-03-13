# VAnnotationLib

用于快速apt开发,对apt进行包装
##Usage([example](myProccessor\src\main\java\com\example\Myprocessor.java))

1. 导入项目下```aptlib```模块

   在```aptlib```下已添加依赖库:

  ```AutoService```(compile 'com.google.auto.service:auto-service:1.0-rc2')

  ```javapoet```(compile 'com.squareup:javapoet:1.4.0')

2. 创建你的注解类并继承```Vprocessor```,并添加注解```@AutoService(Processor.class)```

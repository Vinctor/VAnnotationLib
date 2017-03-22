# VAnnotationLib

用于快速apt开发,对apt进行包装
## 使用
[example](myProccessor/src/main/java/com/example/Myprocessor.java)

#### 添加依赖
>compile 'com.vinctor:annotationlib:0.0.1'
#### 创建你的注解类

继承```Vprocessor```,并添加注解```@AutoService(Processor.class)```

#### 实现方法
1.```getSupportedAnnotations```(返回需要处理注解的class数组)

2.```process```(处理注解主要方法)

note:主要工具类```EntityHandler```,用户对注解进行包装,其已在父类中进行初始化,直接调用```entityHandler.handlerElement(roundEnv, this)```进行调用,
返回已经包装完毕的``` Map<String, ClassEntity>```,你可以对此进行进一步进行处理,如代码生成或者其他操作.

#### 其他
1.在```process```方法中调用```VLog.setDebug(true)```打开日志,应该在调用```entityHandler.handlerElement(roundEnv, this)```之前调用

2如果您自己需要自定义一些规则,如:字段不能为```private```或方法不能为```private```或方法不能为```abstract```类型

你可以在调用```entityHandler.handlerElement(roundEnv, this)```之前调用```entityHandler.setOnFieldRegulaListener```或者```entityHandler.setOnMethodRegulaListener```对```Element```进行复杂规则的判断

如果符合你的规则,你可以返回```null```或者空字符串,如若该```elemenet```不符合你的规则,
则返回非空字符串,当build时,会报出类似下面错误,错误信息为你返回的字符串

![](https://github.com/Vinctor/VAnnotationLib/blob/master/pic/error.png)

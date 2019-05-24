package com.copia.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
public class MainProcessor extends AbstractProcessor {

    private Types mTypeUtils;

    private Elements mElementUtils;

    private Filer mFiler;

    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println("thom: Processor init");
        mTypeUtils = processingEnv.getTypeUtils();
        // element 可以是类、方法、变量等
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println("thom: Processor types");
        // 注解处理器需要处理的注解
        Set annotations = new LinkedHashSet();
        annotations.add(FieldInject.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        System.out.println("thom: Processor version");
        return super.getSupportedSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("thom: Processor process");
        // 扫描、评估、处理注解的代码，生成java文件
        for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(FieldInject.class)) {
            if (annotatedElement.getKind() != ElementKind.FIELD ) {
                error(annotatedElement,"only field can be annotated with @%s",
                        FieldInject.class.getSimpleName());
                return true;
            }

            analysisAnnotated(annotatedElement);
        }

        return false;
    }

    private void error(Element e, String msg, Object... args) {
        mMessager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }


    private void analysisAnnotated(Element annotatedElement) {
        System.out.println("thom: MainProcessor analysis");
        FieldInject fieldInject = annotatedElement.getAnnotation(FieldInject.class);
        String value = fieldInject.value();


        Modifier[] modifiers = { Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL };
        FieldSpec fieldSpec =FieldSpec.builder(Set.class, "value")
                .initializer(CodeBlock.of("new $T<>()", LinkedHashSet.class))
                .addModifiers(modifiers).build();

//        // field to class
//        String className = annotatedElement.getSimpleName().toString() + "$Proc";

        // field of class
        TypeElement typeElement =(TypeElement) annotatedElement.getEnclosingElement();
//        String className = typeElement.getQualifiedName().toString();  // com.cornucopia.application.Cornucopia
        String className = typeElement.getSimpleName().toString();
        className = className + "$Proc";

        MethodSpec methodSpec = MethodSpec.methodBuilder("getDate")
                .returns(Date.class)
                .addStatement("return new $T()", Date.class)
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addField(fieldSpec)
                .addMethod(methodSpec)
                .build();

        try {
            JavaFile.builder("com.copia.processor", typeSpec)
                    .build()
                    .writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
            error(annotatedElement, "IOException： " + e);
        }


    }

}

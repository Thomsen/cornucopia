package com.cornucopia.tools;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class CopiaProcessor extends AbstractProcessor {

    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> types = new LinkedHashSet<>();
        types.add(CopiaAnnotation.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        HashMap<String, String> nameMap = new HashMap<>();

        Set<? extends Element> annotationElements = roundEnvironment
                .getElementsAnnotatedWith(CopiaAnnotation.class);

        for (Element element : annotationElements) {
            CopiaAnnotation annotation = element.getAnnotation(CopiaAnnotation.class);
            String name = annotation.name();
            nameMap.put(name, element.getSimpleName().toString());
            System.out.println("copia processor annotation: " + element.getSimpleName().toString());
        }

        return false;
    }

}

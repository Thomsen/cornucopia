package com.cornucopia.java;

import com.cornucopia.java.annotation.CopiaMethod;
import com.cornucopia.java.annotation.CopiaProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CopiaJavaMain {

    @CopiaProperty(value = "thomsen")
    public String name;

    public int age;

    public int count;

    public String getName() {
        return name;
    }

    @CopiaMethod(init = 2)
    public int getCount() {
        return count;
    }

    public static void main(String[] args) {

        CopiaJavaMain copia = new CopiaJavaMain();

        // not RetentionPolicy.RUNTIME)
        System.out.println("copia java main " + copia.name);

        copia.name = "copia";

        System.out.println("copia java main " + copia.getName());

        copia.name = "copiaa";

        try {
//        Class clazz = copia.getClass();
            Class<?> clazz = Class.forName("com.cornucopia.java.CopiaJavaMain");  // 装入类
//            Class.forName("com.cornucopia.java.CopiaJavaMain").newInstance() == new CopiaJavaMain()  // 类示例化
            Field[] fields = clazz.getDeclaredFields(); //拿到它定义的所有字段
            for (Field field : fields) {
                if (field.isAnnotationPresent(CopiaProperty.class)) {
                    System.out.println("have annotation");
                }
                // annotation property
                CopiaProperty copiaProperty = field.getAnnotation(CopiaProperty.class);
                if (copiaProperty != null) {
                    System.out.println("annotation: " + copiaProperty.toString());
                    System.out.print("annotation field: " + field.getName() + " -- ");
                    System.out.println("value: " + copiaProperty.value());
                }
                System.out.println("property: " + field.getName() + " ---- " + field.getModifiers());

            }

            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                // annotation method
                CopiaMethod copiaMethod = method.getAnnotation(CopiaMethod.class);
                if (null != copiaMethod) {
                    copia.count = copiaMethod.init();
                    System.out.print("annotation method: " + method.getName() + " ");
                }
                System.out.println("count value: " + copia.getCount());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

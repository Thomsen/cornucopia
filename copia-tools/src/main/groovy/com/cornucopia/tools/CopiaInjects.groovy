package com.cornucopia.tools

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project


class CopiaInjects {

    private final static ClassPool pool = ClassPool.getDefault();

    public static void inject(String path, Project project) {

        pool.appendClassPath(path)

        pool.appendClassPath(project.android.bootClasspath[0].toString())

        pool.importPackage("android.os.Bundle")

        File dir = new File(path)
        if (dir.isDirectory()) {

            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath

                println("inject filepath = " + filePath)

                // dynamic inject code
                if (file.getName().equals("CopiaModule.class")) {
                    CtClass ctClass = pool.getCtClass("com.cornucopia.tools.CopiaModule")

                    if (ctClass.isFrozen()) {
                        ctClass.defrost()

                        //ctClass.frozen
                    }

                    CtMethod ctMethod = ctClass.getDeclaredMethod("inject")

                    println("inject method = " + ctMethod)

                    String insetBefore = """String msg = "inject message"; """

                    ctMethod.insertBefore(insetBefore)

                    ctClass.writeFile(path)  // not path default = cornucopia/com/cornucopia/tools

                    println("inject write = " + path)

                    // copia-app/build/intermediates/classes/debug/com/cornucopia/tools/CopiaModule.class
                    // transform ->
                    // copia-app/build/intermediates/transforms/RealmTransformer/debug/0/com/cornucopia/tools/CopiaModule.class

                    ctClass.detach()

                }
            }
        }
    }
}

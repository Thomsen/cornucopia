package com.cornucopia.tools

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project;

class CopiaPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("testTask") << {
            println "copia plugin task"
        }

        transform(project)
    }

    void transform(Project project) {

        if (project.plugins.hasPlugin(AppPlugin)) {

            // AppExtension = build.gradle android {...}
            def android = project.extensions.getByType(AppExtension)

            def transform = new TransformWrapper(project)
            android.registerTransform(transform)

            project.extensions.create("moduleConfig", CopiaModule)

            android.applicationVariants.all {
                variant ->
                    def variantData = variant.variantData
                    def scope = variantData.scope

                    def config = project.extensions.getByName("moduleConfig")

                    def createTaskName = scope.getTaskName("test", "testPlugin")
                    def createTask = project.task(createTaskName)

                    createTask.doLast {
                        createJavaTest(variant, config)
                    }


                    String generateBuildConfigTaskName = variant.getVariantData().getScope()
                            .getGenerateBuildConfigTask().name

                    def generateBuildConfigTask = project.tasks.getByName(generateBuildConfigTaskName)
                    if (generateBuildConfigTask) {
                        createTask.dependsOn(generateBuildConfigTask)
                        generateBuildConfigTask.finalizedBy(createTask)
                    }
            }
        }

    }

    def createJavaTest(variant, config) {
        def content = """
                      package com.cornucopia.tools;

                      class CopiaModule {
                        public static final String module = "${config.module}";

                        public boolean inject() {
                          return false;
                        }
                      }
                      """;

        File outputDir = variant.getVariantData().getScope().getBuildConfigSourceOutputDir()

        def javaFile = new File(outputDir, "CopiaModule.java")

        javaFile.write(content, 'UTF-8')
    }
}
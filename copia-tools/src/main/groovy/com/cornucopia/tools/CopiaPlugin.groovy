package com.cornucopia.tools

import org.gradle.api.Plugin
import org.gradle.api.Project;

class CopiaPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("testTask") << {
            println "copia plugin task"
        }
    }

}
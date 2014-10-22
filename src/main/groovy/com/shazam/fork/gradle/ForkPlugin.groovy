/*
 * Copyright 2014 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.shazam.fork.gradle

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.TestVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin

/**
 * Gradle plugin for Fork.
 */
class ForkPlugin implements Plugin<Project> {

    /** Task name prefix. */
    private static final String TASK_PREFIX = "fork"

    @Override
    void apply(final Project project) {

        if (!project.plugins.withType(AppPlugin)) {
            throw new IllegalStateException("Android plugin is not found")
        }

        project.extensions.add "fork", ForkExtension

        def forkTask = project.task("fork") {
            group = JavaBasePlugin.VERIFICATION_GROUP
            description = "Runs all the instrumentation test variations on connected devices"
        }

        AppExtension android = project.android
        android.testVariants.all { TestVariant variant ->

            String taskName = "${TASK_PREFIX}${variant.name.capitalize()}"
            ForkRunTask task = createTask(taskName, variant, project)

            task.configure {
                description = "Runs instrumentation tests on connected devices for '${variant.name}' variation and generates a report with screenshots"
            }

            forkTask.dependsOn task
        }

        project.tasks.addRule(patternString("fork")) { String ruleTaskName ->
            if (ruleTaskName.startsWith("fork")) {
                String suffix = lowercase(ruleTaskName - "fork")
                if (android.testVariants.find { suffix.startsWith(it.name) } != null) {
                    // variant specific, not our case
                    return
                }
                String size = suffix.toLowerCase(Locale.US)
                if (isValidSize(size)) {
                    def variantTaskNames = forkTask.taskDependencies.getDependencies(forkTask).collect() { it.name }
                    project.task(ruleTaskName, dependsOn: variantTaskNames.collect() { "${it}${size}" })
                }
            }
        }
    }

    private static String patternString(final String taskName) {
        return "Pattern: $taskName<TestSize>: run instrumentation tests of particular size"
    }

    private static ForkRunTask createTask(final String name, final TestVariant variant, final Project project) {
        ForkExtension config = project.fork
        ForkRunTask task = project.tasks.create(name, ForkRunTask)

        task.configure {
            group = JavaBasePlugin.VERIFICATION_GROUP
            applicationApk = variant.testedVariant.outputFile
            instrumentationApk = variant.outputFile

            File outputBase = config.baseOutputDir
            if (!outputBase) {
                outputBase = new File(project.buildDir, "fork")
            }
            output = new File(outputBase, variant.testedVariant.dirName)
            ignoreFailures = config.ignoreFailures

            dependsOn variant.assemble, variant.testedVariant.assemble
        }
    }

}

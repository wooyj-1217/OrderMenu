import org.jlleitschuh.gradle.ktlint.KtlintExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    kotlin("jvm") version "1.9.0" // or kotlin("multiplatform") or any other kotlin plugin
    kotlin("plugin.serialization") version "1.9.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}


// Lint
allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<KtlintExtension> {
        version.set("1.2.1")
        outputColorName.set("RED")
        android.set(true)
    }
}

subprojects {
    tasks {
        afterEvaluate {
            val predicate = { task: Task ->
                task.name.startsWith("preBuild") || task.name.startsWith("compile")
            }
            container
                .filter { predicate(it) }
                .forEach {
                    it.dependsOn("ktlintCheck")
                }
        }
    }
}
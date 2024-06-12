import org.jlleitschuh.gradle.ktlint.KtlintExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    kotlin("jvm") version "1.9.24" // or kotlin("multiplatform") or any other kotlin plugin
    kotlin("plugin.serialization") version "1.9.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
}

// Lint
allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<KtlintExtension> {
        version.set("1.3.0")
        outputColorName.set("RED")
        android.set(true)
    }
}

dependencies {
    ktlintRuleset("io.nlopez.compose.rules:ktlint:0.4.4")
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

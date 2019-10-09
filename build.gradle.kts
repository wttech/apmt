import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import pl.allegro.tech.build.axion.release.domain.TagNameSerializationConfig

description = "AEM Permisson Matrix Tester"

plugins {
    id("pl.allegro.tech.build.axion-release") version "1.10.2"
    kotlin("jvm") version "1.3.31" apply false
    `maven-publish`
    signing
}

scmVersion {
    useHighestVersion = true
    ignoreUncommittedChanges = false
    tag(closureOf<TagNameSerializationConfig> {
        prefix = "apmt"
    })
}

project.version = scmVersion.version

allprojects {
    group = "com.cognifide.apmt"

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

apply(from = "gradle/common.gradle.kts")

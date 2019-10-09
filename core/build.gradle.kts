plugins {
    kotlin("jvm")
    `maven-publish`
    signing
}

dependencies {
    implementation("io.rest-assured:rest-assured:4.0.0")
    implementation("org.yaml:snakeyaml:1.19")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.8")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    implementation("org.apache.commons:commons-lang3:3.8.1")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.23.2")
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allJava)
    archiveClassifier.set("sources")
}

tasks.register<Jar>("javadocJar") {
    from(tasks.javadoc)
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("apmt") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
            afterEvaluate {
                artifactId = "apmt"
                version = rootProject.version
            }
        }
    }
}

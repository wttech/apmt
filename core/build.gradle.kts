plugins {
    kotlin("jvm")
}

dependencies {
    implementation("io.rest-assured:rest-assured:4.0.0")
    implementation("org.yaml:snakeyaml:1.19")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.8")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    implementation("com.github.tomakehurst:wiremock-jre8:2.23.2")
    implementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    implementation("org.junit.jupiter:junit-jupiter-params:5.5.1")
}

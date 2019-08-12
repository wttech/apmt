plugins {
    kotlin("jvm")
}

dependencies {
    testImplementation("com.google.guava:guava:26.0-jre")
    testImplementation(project(":core"))
}

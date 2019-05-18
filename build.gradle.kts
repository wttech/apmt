import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31" apply false
    id("org.jlleitschuh.gradle.ktlint") version "8.0.0"
}

repositories {
    jcenter()
}

description = "AEM Permisson Matrix Tester"

subprojects {
    repositories {
        mavenCentral()
    }

    plugins.withId("kotlin") {
        tasks.named<Test>("test") {
            useJUnitPlatform()
            testLogging {
                events = setOf(
                    TestLogEvent.STANDARD_OUT,
                    TestLogEvent.STANDARD_ERROR,
                    TestLogEvent.FAILED,
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED
                )
            }
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }

        dependencies {
            "implementation"(kotlin("stdlib-jdk8"))
            "implementation"("org.junit.jupiter:junit-jupiter-api:5.4.2")
            "testRuntime"("org.junit.jupiter:junit-jupiter-engine:5.4.2")

            "implementation"("org.apache.commons:commons-lang3:3.8.1")
        }
    }
}

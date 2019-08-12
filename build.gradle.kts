import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31" apply false
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
            "implementation"("org.junit.jupiter:junit-jupiter-api:5.5.1")
            "implementation"("org.junit.jupiter:junit-jupiter-params:5.5.1")
            "testRuntime"("org.junit.jupiter:junit-jupiter-engine:5.5.1")

            "implementation"("org.apache.commons:commons-lang3:3.8.1")
        }
    }
}

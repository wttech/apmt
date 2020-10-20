/*
 * ========================LICENSE_START=================================
 * AEM Permission Management
 * %%
 * Copyright (C) 2013 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *       http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

import org.gradle.api.tasks.testing.logging.TestLogEvent

allprojects {
    repositories {
        mavenLocal()
        jcenter()
    }

    plugins.withId("kotlin") {
        tasks.withType<Test>().configureEach {
            failFast = true
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

        dependencies {
            "implementation"(kotlin("stdlib-jdk8"))
            "implementation"("org.junit.jupiter:junit-jupiter-api:5.5.1")
            "implementation"("org.junit.jupiter:junit-jupiter-params:5.5.1")
            "testRuntime"("org.junit.jupiter:junit-jupiter-engine:5.5.1")
        }
    }

    afterEvaluate {
        val apmtRepositoryUsername: String? by extra
        val apmtRepositoryPassword: String? by extra
        extensions.findByType(PublishingExtension::class)?.apply {
            publications?.findByName("apmt")?.apply {
                if (this is MavenPublication) {
                    pom {
                        name.set("APMT")
                        description.set("AEM Permission Matrix Tester")
                        url.set("https://github.com/Cognifide/apmt")
                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }
                        developers {
                            developer {
                                name.set("Marcin Jędraszczyk")
                                email.set("marcin.jedraszczyk@cognifide.com")
                                organization.set("Cognifide")
                                organizationUrl.set("https://www.cognifide.com")
                            }
                            developer {
                                name.set("Michał Krzyżanowski")
                                email.set("michal.krzyzanowski@cognifide.com")
                                organization.set("Cognifide")
                                organizationUrl.set("https://www.cognifide.com")
                            }
                            developer {
                                name.set("Bartosz Zbytniewski")
                                email.set("bartosz.zbytniewski@cognifide.com")
                                organization.set("Cognifide")
                                organizationUrl.set("https://www.cognifide.com")
                            }
                        }
                        scm {
                            connection.set("https://github.com/Cognifide/apmt.git")
                            developerConnection.set("https://github.com/Cognifide/apmt.git")
                            url.set("https://github.com/Cognifide/apmt")
                        }
                    }
                }
            }
            repositories {
                maven {
                    name = "OSSSonatypeOrg"
                    url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                    credentials {
                        username = apmtRepositoryUsername
                        password = apmtRepositoryPassword
                    }
                    authentication {
                        create<BasicAuthentication>("basic")
                    }
                }
            }
        }

        extensions.findByType(SigningExtension::class)?.apply {
            val signingKey: String? by project
            val signingPassword: String? by project
            useInMemoryPgpKeys(signingKey, signingPassword)
            val publication = extensions.findByType(PublishingExtension::class)?.publications?.findByName("apmt")
            if (publication != null) {
                sign(publication)
            }
        }
    }
}
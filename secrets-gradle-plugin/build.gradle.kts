// Copyright 2021 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

plugins {
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.12.0"
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly("com.android.tools.build:gradle:4.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.10")
    testImplementation("com.android.tools.build:gradle:4.1.1")
    testImplementation("junit:junit:4.13.1")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}

gradlePlugin {
    plugins {
        create(PluginInfo.name) {
            id = "${PluginInfo.group}.${PluginInfo.artifactId}"
            implementationClass = PluginInfo.implementationClass
        }
    }
}

pluginBundle {
    website = PluginInfo.url
    vcsUrl = PluginInfo.url
    description = PluginInfo.description
    version = PluginInfo.version

    (plugins) {
        PluginInfo.name {
            displayName = "Secrets Gradle Plugin for Android"
            tags = listOf("kotlin", "android")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenPub") {
            group = PluginInfo.group
            artifactId = PluginInfo.artifactId
            version = PluginInfo.version

            pom {
                name.set(PluginInfo.artifactId)
                description.set(PluginInfo.description)
                url.set(PluginInfo.url)

                scm {
                    connection.set("scm:git@github.com:google/secrets-gradle-plugin.git")
                    developerConnection.set("scm:git@github.com:google/secrets-gradle-plugin.git")
                    url.set(PluginInfo.url)
                }

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }

                organization {
                    name.set("Google Inc.")
                    url.set("https://developers.google.com/maps")
                }


                developers {
                    developer {
                        name.set("Google Inc.")
                    }
                }
            }
        }
    }
    repositories {
        maven(url = "build/repository")
    }
}

object PluginInfo {
    const val artifactId = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin.gradle.plugin"
    const val description = "A Gradle plugin for providing secrets securely to an Android project."
    const val group = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
    const val implementationClass = "com.google.android.libraries.mapsplatform.secrets_gradle_plugin.SecretsPlugin"
    const val name = "secretsGradlePlugin"
    const val url = "https://github.com/google/secrets-gradle-plugin"
    const val version = "1.1.0"
}

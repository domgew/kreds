import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 *  Copyright (C) 2021 Abhijith Shivaswamy
 *   See the notice.md file distributed with this work for additional
 *   information regarding copyright ownership.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

plugins {
    kotlin("multiplatform") version "1.9.22"
    id("org.jetbrains.dokka") version "1.9.10"
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    `maven-publish`
    signing
    id("io.gitlab.arturbosch.detekt") version "1.23.4"
}

group = "io.github.crackthecodeabhi"
version = "0.9.1"

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
                implementation("io.netty:netty-codec-redis:4.1.104.Final")
                implementation("io.netty:netty-handler:4.1.104.Final")
                implementation(kotlin("stdlib"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
            tasks.all {
                if (this is JavaCompile) {
                    targetCompatibility = "11"
                    sourceCompatibility = "17"
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5:5.6.2")
                implementation("io.kotest:kotest-assertions-core:5.5.4")
                implementation("net.swiftzer.semver:semver:1.3.0")
                implementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
                implementation("ch.qos.logback:logback-classic:1.4.14")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine")
            }
            tasks.all {
                if (this is Test) {
                    useJUnitPlatform()
                    systemProperty(
                        "REDIS_PORT",
                        System.getProperty("REDIS_PORT") ?: "6379",
                    )
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(System.getProperty("SONATYPE_USERNAME"))
            password.set(System.getProperty("SONATYPE_PASSWORD"))
        }
    }
}

signing {
    useInMemoryPgpKeys(
        System.getProperty("GPG_PRIVATE_KEY"),
        System.getProperty("GPG_PRIVATE_PASSWORD")
    )
    sign(publishing.publications)
}

detekt {
    buildUponDefaultConfig = true
    config = files(rootDir.resolve("detekt.yml"))
    parallel = true
    ignoreFailures = true
    reports {
        html.enabled = false
        txt.enabled = false
    }
}


import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.21"

    // The buildscript is also kotlin, so we apply at the root level
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    // We need to declare these here since we are configuring them for
    // subprojects from the top level.
    `jacoco`
    `maven-publish`
    `signing`
    id("com.github.ben-manes.versions") version "0.20.0"
    id("org.jetbrains.dokka") version "0.10.0" apply false

    // We apply this so that ktlint can format the top level buildscript
    id("org.jlleitschuh.gradle.ktlint") version "7.2.1"
}

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

subprojects {

    val group = "de.zalando"

    val projVersion = System.getenv("VERSION") ?: "1.0.0-dev"

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "com.github.ben-manes.versions")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "maven-publish")
    apply(plugin = "jacoco")
    apply(plugin = "signing")

    kapt {
        includeCompileClasspath = false
    }

    tasks.withType(KotlinCompile::class.java).all {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType(DokkaTask::class.java).all {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/dokka"
        configuration {
            reportUndocumented = false
        }
    }

    tasks.register("javadocJar", Jar::class) {
        dependsOn(tasks["dokka"])
        archiveClassifier.set("javadoc")
        from(tasks["dokka"])
    }

    tasks.register("sourcesJar", Jar::class) {
        dependsOn(JavaPlugin.CLASSES_TASK_NAME)
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    artifacts {
        add("archives", tasks["javadocJar"])
        add("archives", tasks["sourcesJar"])
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                groupId = group
                artifactId = project.name
                version = if (projVersion.endsWith("-dev")) projVersion.replace("-dev", "-SNAPSHOT") else projVersion

                from(components["java"])
                artifact(tasks["sourcesJar"])
                artifact(tasks["javadocJar"])
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/cortexapps/zally-cortex")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_PASSWORD")
                }
            }
        }
    }

    configurations.all {
        resolutionStrategy {
            // 1.2.10 disallows jar:file: resources, hopefully fixed in 1.2.14+
            force("com.github.java-json-tools:json-schema-core:bf09fe87139ac1fde0755194b59130f3b2d63e3a")
        }
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib")

        // We define this here so all subprojects use the same version of jackson
        compile("com.fasterxml.jackson.module:jackson-module-parameter-names:2.10.2")
        compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.10.2")
        compile("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.10.2")
        compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.2")
        compile("org.yaml:snakeyaml:1.24")

        testCompile("com.jayway.jsonpath:json-path-assert:2.4.0")
        testCompile("org.mockito:mockito-core:2.23.4")
    }

    jacoco {
        toolVersion = "0.8.2"
    }

    tasks.check {
        dependsOn(tasks.jacocoTestReport)
    }

    tasks.jacocoTestReport {
        reports {
            xml.isEnabled = true
        }
    }

    tasks.jar {
        archiveBaseName.set(project.name)
        archiveVersion.set(version)
    }
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = "5.3.1"
}

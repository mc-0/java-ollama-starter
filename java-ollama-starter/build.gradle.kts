plugins {
    id("java-library")
    kotlin("jvm") version "1.6.21"
    `maven-publish`  // Apply the maven-publish plugin for publishing
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "org.mc0"
version = "1.0.0"
description = "Ollama Starter Library"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")  // Jackson
    compileOnly("org.projectlombok:lombok:1.18.24")  // Lombok
    annotationProcessor("org.projectlombok:lombok:1.18.24")  // Lombok annotation processor

    // Spring Boot dependencies
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.2.0")
    implementation("org.springframework.boot:spring-boot-starter:3.2.0")

    // Add the missing dependency explicitly
    implementation("org.springframework:spring-context:6.0.0")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.2.0")  // Spring Boot configuration processor
}


dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.0")  // Manages Spring Boot versions
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])  // Publishes the Java components
        }
    }
    repositories {
        maven {
            url = uri("${buildDir}/repo")  // Publish to a local repository in the build directory
        }
    }
}

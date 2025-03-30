
plugins {
    id("java")
    id("java-library")
    kotlin("jvm") version "1.6.21" // Apply the Kotlin plugin
    `maven-publish`  // Apply the maven-publish plugin
}

group = "org.mc0"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Spring Boot Auto-Configuration (Required for @ConfigurationProperties)
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.2.0")

    // Enable property binding support
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.2.0")
}

tasks.test {
    useJUnitPlatform()
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

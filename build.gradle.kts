import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.ryandens.javaagent-test") version "0.8.0"
}

group = "cc.abro"
version = "0.1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // General libs
    val springBootVersion = "3.5.3"
    val micrometerVersion = "1.15.0"
    val lombokVersion = "1.18.38"
    val testcontainersVersion = "1.21.1"
    val buddyAgentVersion = "1.17.5"
    implementation("org.springframework.boot:spring-boot-starter-actuator:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-validation:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    developmentOnly("org.springframework.boot:spring-boot-devtools:${springBootVersion}")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus:${micrometerVersion}")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${springBootVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}")
    testImplementation("org.springframework.boot:spring-boot-testcontainers:${springBootVersion}")
    testImplementation("org.testcontainers:junit-jupiter:${testcontainersVersion}")
    testJavaagent("net.bytebuddy:byte-buddy-agent:${buddyAgentVersion}")

    // Postgresql
    val postgresqlVersion = "42.7.7"
    val liquibaseVersion = "4.32.0"
    implementation("org.liquibase:liquibase-core:${liquibaseVersion}")
    runtimeOnly("org.postgresql:postgresql:${postgresqlVersion}")
    testImplementation("org.testcontainers:postgresql:${testcontainersVersion}")

    // Kafka
    val kafkaVersion = "3.3.6"
    implementation("org.springframework.kafka:spring-kafka:${kafkaVersion}")
    testImplementation("org.springframework.kafka:spring-kafka-test:${kafkaVersion}")
    testImplementation("org.testcontainers:kafka:${testcontainersVersion}")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis:${springBootVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<BootJar>("bootJar") {
    archiveFileName.set("app.jar")
}

apply(from = "docker.gradle.kts")
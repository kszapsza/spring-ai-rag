plugins {
    java
    alias(libs.plugins.integration.test)
    alias(libs.plugins.test.logger)
}

group = "io.github.kszapsza"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(libs.micrometer.registry.prometheus)
    implementation(libs.postgresql)
    implementation(libs.spring.ai.openai.spring.boot.starter)
    implementation(libs.spring.ai.pgvector.store.spring.boot.starter)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.web)

    testImplementation(libs.spring.boot.starter.test)

    testRuntimeOnly(libs.junit.platform.launcher)

    integrationImplementation(libs.bundles.testcontainers)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

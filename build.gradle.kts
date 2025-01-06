plugins {
    application
}

application {
    mainClass.set("io.github.kszapsza.springairag.SpringAiRagApplication")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation(project(":backend"))
    implementation(project(":frontend"))
}

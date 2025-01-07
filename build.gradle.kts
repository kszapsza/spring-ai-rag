plugins {
    application
}

application {
    mainClass.set("io.github.kszapsza.springairag.SpringAiRagApplication")
}

allprojects {
    apply(plugin = "java")
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }
}

dependencies {
    implementation(project(":backend"))
    implementation(project(":frontend"))
}

import com.github.gradle.node.yarn.task.YarnTask

plugins {
    `java-library`
    id("com.github.node-gradle.node") version "7.1.0"
}

node {
    download = true
    version = "23.1.0"
    yarnVersion= "1.22.22"
}

tasks.build {
    dependsOn(tasks.named("yarnBuild"))
}

tasks.register<YarnTask>("yarnInstall") {
    args.set(listOf("install"))
}

tasks.register<YarnTask>("yarnBuild") {
    dependsOn(tasks.named("yarnInstall"))
    args.set(listOf("build"))
    inputs.dir(project.fileTree("src").exclude("**/*.test.ts"))
    inputs.dir(project.fileTree("public"))
    inputs.files("*.html", "*.json", "*.ts", ".tsx", "*.js", ".jsx")
    outputs.dir(project.layout.buildDirectory.dir("dist"))
}

tasks.jar {
    dependsOn(tasks.named("yarnBuild"))
    from(project.layout.buildDirectory.dir("dist"))
}

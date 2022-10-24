import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
}

java.sourceCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation(project(":common-tools"))

    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("io.swagger.core.v3:swagger-annotations:2.1.12")
    implementation("javax.validation:validation-api:2.0.1.Final")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

configurations {
    create("test")
}

tasks.register<Jar>("testJar") {
    archiveBaseName.set("${project.name}-test")
    from(sourceSets.test.get().output)
}

artifacts {
    add("test", tasks["testJar"])
}
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
}

java.sourceCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation(project(":common-tools"))
    implementation(project(":student-manager-api"))
    implementation(project(":course-manager-api"))

    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("io.swagger.core.v3:swagger-annotations:2.1.12")

    testImplementation(project(":common-tools", "test"))
    testImplementation(project(":student-manager-api", "test"))
    testImplementation(project(":course-manager-api", "test"))
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

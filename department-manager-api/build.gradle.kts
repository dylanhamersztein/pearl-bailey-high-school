import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
}

java.sourceCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation(project(":common-tools"))
    implementation(project(":teacher-manager-api"))

    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.4")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.4")

    testImplementation(project(":common-tools", "test"))
    testImplementation(project(":teacher-manager-api", "test"))
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

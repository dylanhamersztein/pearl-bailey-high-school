plugins {
    id("io.spring.dependency-management")
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

java.sourceCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation(project(":common-tools"))
    implementation(project(":teacher-manager-api"))
    implementation(project(":department-manager-api"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.flywaydb:flyway-core")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.4")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.4")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation(project(":common-tools", "test"))
    testImplementation(project(":teacher-manager-api", "test"))
    testImplementation(project(":department-manager-api", "test"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.withType<Test> {
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    useJUnitPlatform()
}

plugins {
    id(Plugins.Id.springBoot)

    kotlin(Plugins.Module.spring)
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Dependencies.Cruel.core))
    implementation(project(Dependencies.Cruel.cruelMysql))

    implementation(Dependencies.Spring.web)
    implementation(Dependencies.Spring.security)
    implementation(Dependencies.Spring.actuator)
    implementation(Dependencies.Spring.validation)
    implementation(Dependencies.Spring.log4j2)
    implementation(Dependencies.Spring.springDoc)

    implementation(Dependencies.Kotlin.jackson)
    implementation(Dependencies.Kotlin.jacksonDataType)

    annotationProcessor(Dependencies.Spring.configProcessor)

    testImplementation(Dependencies.Spring.starterTest)
    testImplementation(Dependencies.Spring.securityTest)
    implementation(Dependencies.Jwt.api)
    runtimeOnly(Dependencies.Jwt.impl)
    runtimeOnly(Dependencies.Jwt.jackson)
}

configurations {
    all {
        exclude("ch.qos.logback", "logback-classic")
        exclude("org.apache.logging.log4j", "log4j-to-slf4j")
    }

    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

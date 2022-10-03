import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

plugins {
    kotlin(Plugins.Module.jpa)
}

dependencies {
    implementation(Dependencies.Data.jpa)
    implementation(Dependencies.Data.jdsl)
}

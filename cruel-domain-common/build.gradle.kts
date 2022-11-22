import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

plugins {
    id(Plugins.Id.springBoot)
    id(Plugins.Id.springDependencyManagement)

    kotlin(Plugins.Module.spring)
    kotlin(Plugins.Module.jpa)
    kotlin(Plugins.Module.allOpen)
}

dependencies {
    implementation(project(Dependencies.Cruel.core))
    implementation(Dependencies.Data.jpa)
    testImplementation(Dependencies.Spring.starterTest)
}

plugins {
    id(Plugins.Id.springBoot)

    kotlin(Plugins.Module.spring)
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

dependencies {
    implementation(project(Dependencies.Cruel.core))
    implementation(project(Dependencies.Cruel.common))
    implementation(project(Dependencies.Cruel.cruelMysql))
    implementation(Dependencies.Spring.starterBatch)

    implementation(Dependencies.Spring.log4j2)
    implementation(Dependencies.Data.jpa)

    annotationProcessor(Dependencies.Spring.configProcessor)
}

configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }

    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencyManagement {
    imports {
        mavenBom(Dependencies.Spring.cloudDependencies)
    }
}

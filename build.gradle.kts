import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.Id.springBoot) version Versions.springBootPlugin apply false
    id(Plugins.Id.springDependencyManagement) version Versions.springDependencyManagementPlugin apply false
    id(Plugins.Id.ktlint) version Versions.ktlint

    kotlin(Plugins.Module.jvm) version Versions.kotlinPlugin
    kotlin(Plugins.Module.spring) version Versions.kotlinPlugin apply false
    kotlin(Plugins.Module.jpa) version Versions.kotlinPlugin apply false
    kotlin(Plugins.Module.allOpen) version Versions.kotlinPlugin apply false
}

allprojects {
    group = "io.fana.cruel"
    version = "0.0.1"

    // for fix kotest bug
    // @see https://github.com/kotest/kotest/issues/1603#issuecomment-801027459
    extra["kotlin-coroutines.version"] = Versions.coroutines

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = Versions.jvmTarget
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

subprojects {
    apply {
        plugin(Plugins.Id.idea)
        plugin(Plugins.Id.kotlin)
        plugin(Plugins.Id.kotlinSpring)
        plugin(Plugins.Id.kotlinAllOpen)
        plugin(Plugins.Id.springBoot)
        plugin(Plugins.Id.springDependencyManagement)
        plugin(Plugins.Id.ktlint)
    }
    dependencies {
        implementation(Dependencies.Kotlin.reflect)
        implementation(Dependencies.Kotlin.jvm)

        implementation(Dependencies.Logging.log4j2Api)
        implementation(Dependencies.Logging.log4j2Core)
        implementation(Dependencies.Logging.jsonTemplateLayout)

        testImplementation(Dependencies.Test.kotestRunnerJunit)
        testImplementation(Dependencies.Test.kotestAssertions)
        testImplementation(Dependencies.Test.kotestProperty)
        testImplementation(Dependencies.Test.mockk)
        testImplementation(Dependencies.Test.springMockk)
        testImplementation(Dependencies.Test.kotlinFixture)
        api(Dependencies.Test.faker)
    }
}

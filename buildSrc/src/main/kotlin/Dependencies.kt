object Dependencies {
    object Kotlin {
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect"
        const val jvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
        const val jackson = "com.fasterxml.jackson.module:jackson-module-kotlin"
        const val jacksonDataType = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310"
    }

    object Spring {
        const val starter = "org.springframework.boot:spring-boot-starter"
        const val web = "org.springframework.boot:spring-boot-starter-web"
        const val retry = "org.springframework.retry:spring-retry"
        const val configProcessor = "org.springframework.boot:spring-boot-configuration-processor"
        const val security = "org.springframework.boot:spring-boot-starter-security"
        const val validation = "org.springframework.boot:spring-boot-starter-validation"
        const val feign = "org.springframework.cloud:spring-cloud-starter-openfeign"
        const val devtools = "org.springframework.boot:spring-boot-devtools"
        const val starterTest = "org.springframework.boot:spring-boot-starter-test"
        const val securityTest = "org.springframework.security:spring-security-test"
        const val cloudDependencies = "org.springframework.cloud:spring-cloud-dependencies:${Versions.springCloud}"
        const val springDoc = "org.springdoc:springdoc-openapi-ui:${Versions.springDoc}"
        const val log4j2 = "org.springframework.boot:spring-boot-starter-log4j2"
        const val actuator = "org.springframework.boot:spring-boot-starter-actuator"
    }

    object Jwt {
        const val api = "io.jsonwebtoken:jjwt-api:${Versions.jjwt}"
        const val impl = "io.jsonwebtoken:jjwt-impl:${Versions.jjwt}"
        const val jackson = "io.jsonwebtoken:jjwt-jackson:${Versions.jjwt}"
    }

    object Data {
        const val jpa = "org.springframework.boot:spring-boot-starter-data-jpa"
        const val jdsl = "com.linecorp.kotlin-jdsl:hibernate-kotlin-jdsl:${Versions.kotlinJdsl}"
        const val redis = "org.springframework.boot:spring-boot-starter-data-redis"
        const val h2 = "com.h2database:h2"
        const val mysqlConnector = "mysql:mysql-connector-java"
        const val envers = "org.springframework.data:spring-data-envers"
    }

    object Test {
        const val kotestRunnerJunit = "io.kotest:kotest-runner-junit5:${Versions.kotest}"
        const val kotestAssertions = "io.kotest:kotest-assertions-core:${Versions.kotest}"
        const val kotestProperty = "io.kotest:kotest-property:${Versions.kotest}"
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
        const val springMockk = "com.ninja-squad:springmockk:${Versions.springMockk}"
        const val kotlinFixture = "com.appmattus.fixture:fixture:${Versions.kotlinFixture}"
    }

    object Logging {
        const val log4j2Api = "org.apache.logging.log4j:log4j-api:${Versions.log4j2}"
        const val log4j2Core = "org.apache.logging.log4j:log4j-core:${Versions.log4j2}"
        const val jsonTemplateLayout =
            "org.apache.logging.log4j:log4j-layout-template-json:${Versions.log4j2}"
    }

    object Cruel {
        const val core = ":cruel-core"
        const val common = ":cruel-domain-common"
        const val cruelMysql = ":cruel-domain-mysql"
    }
}

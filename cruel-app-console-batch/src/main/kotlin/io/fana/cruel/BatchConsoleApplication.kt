package io.fana.cruel

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import java.util.TimeZone
import javax.annotation.PostConstruct
import kotlin.system.exitProcess

@EnableBatchProcessing
@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
class BatchConsoleApplication

fun main(args: Array<String>) {
    exitProcess(
        SpringApplication.exit(
            runApplication<BatchConsoleApplication>(*args)
        )
    )
}

@PostConstruct
fun setTimeZone() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
}

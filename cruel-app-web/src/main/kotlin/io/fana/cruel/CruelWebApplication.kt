package io.fana.cruel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import java.util.TimeZone
import javax.annotation.PostConstruct

@EnableAsync
@ConfigurationPropertiesScan
@SpringBootApplication
class CruelWebApplication

fun main(args: Array<String>) {
    runApplication<CruelWebApplication>(*args)
}

@PostConstruct
fun setTimeZone() {
    TimeZone.setDefault(TimeZone.getTimeZone("KST"))
}

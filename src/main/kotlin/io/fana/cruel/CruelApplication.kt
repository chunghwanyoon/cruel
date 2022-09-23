package io.fana.cruel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CruelApplication

fun main(args: Array<String>) {
    runApplication<CruelApplication>(*args)
}

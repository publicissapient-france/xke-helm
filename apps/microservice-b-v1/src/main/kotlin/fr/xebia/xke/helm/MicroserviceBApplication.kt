package fr.xebia.xke.helm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MicroserviceBApplication

fun main(args: Array<String>) {
    runApplication<MicroserviceBApplication>(*args)
}

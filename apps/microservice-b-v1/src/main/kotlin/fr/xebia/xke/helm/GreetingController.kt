package fr.xebia.xke.helm

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class GreetingController {
    @GetMapping
    fun get() = "I am Microservice B (v1)"
}
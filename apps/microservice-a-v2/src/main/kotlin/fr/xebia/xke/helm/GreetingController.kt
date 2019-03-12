package fr.xebia.xke.helm

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class GreetingController(
        @Value("\${kubernetes.pod.name}") val podName: String,
        @Value("\${kubernetes.pod.ip}") val podIp: String
) {

    @GetMapping
    fun get(): String {
        val addr = java.net.InetAddress.getLocalHost()
        return "I am Microservice A (v2) on $addr. PodName=$podName, PodIp=$podIp"
    }
}
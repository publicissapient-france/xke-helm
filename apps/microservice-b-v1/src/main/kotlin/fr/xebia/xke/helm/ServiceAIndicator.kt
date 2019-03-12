package fr.xebia.xke.helm

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity


@Service
class ServiceAIndicator(
        restTemplateBuilder: RestTemplateBuilder,
        @Value("\${service.a.url}") val serviceAUrl: String) : AbstractHealthIndicator() {

    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    override fun doHealthCheck(builder: Health.Builder) {
        val status = check()
        if (status.error) {
            builder.down().withDetail("Details", status.message)
        } else builder.up()
    }

    private fun check(): CheckStatus = try {
        val response = restTemplate.getForEntity<ActuatorResponse>("$serviceAUrl/actuator/health")
        if (response.statusCode == HttpStatus.OK)
            CheckStatus(error = false) else CheckStatus(error = true, message = "Service A response status: ${response.statusCode} with response: ${response.body}")
    } catch (e: ResourceAccessException) {
        CheckStatus(error = true, message = "Service not responded")
    }

}

data class ActuatorResponse(
        val status: String
)

data class CheckStatus(
        val error: Boolean,
        val message: String? = null
)

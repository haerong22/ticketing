package io.haerong22.ticketing.interfaces.controller

import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthController(
    private val env: Environment,
) {

    @GetMapping
    fun healthCheck(): String {
        val profile = env.activeProfiles.firstOrNull() ?: "default"
        return "service available on $profile"
    }
}

package io.fana.cruel.app.common.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/status")
class HealthCheckController {
    @GetMapping
    fun health() {
    }
}

package io.fana.cruel.app.common.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Health Check API", description = "Health Check API")
@RestController
@RequestMapping("/api/v1/status")
class HealthCheckController {
    @Operation(operationId = "healthCheck", description = "Health Check")
    @ApiResponse(description = "Health Check", responseCode = "200")
    @GetMapping
    fun health(): ResponseEntity<String> = ResponseEntity.status(HttpStatus.OK).body("HEALTH")
}

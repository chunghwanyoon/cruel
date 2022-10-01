package io.fana.cruel.app.v1.common

import io.fana.cruel.app.common.logger
import io.fana.cruel.core.exception.BusinessException
import io.fana.cruel.core.exception.HttpErrorResponse
import io.fana.cruel.core.exception.client.ClientException
import io.fana.cruel.core.exception.server.ServerException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class BusinessExceptionHandler {
    private val log = logger()

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(exception: BusinessException): ResponseEntity<HttpErrorResponse> {
        when (exception) {
            is ClientException -> log.warn("ClientException: ${exception.message}")
            is ServerException -> {
                log.error("ServerException: ${exception.message}")
            }

            else -> {}
        }
        return ResponseEntity
            .status(exception.statusCode.value)
            .body(HttpErrorResponse.of(exception.message!!, exception.errorCode))
    }
}

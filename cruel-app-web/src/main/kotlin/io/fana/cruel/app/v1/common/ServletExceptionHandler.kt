package io.fana.cruel.app.v1.common

import io.fana.cruel.app.common.logger
import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.HttpErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class ServletExceptionHandler {
    private val log = logger()

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException,
    ): ResponseEntity<HttpErrorResponse> {
        log.info("handleMethodArgumentNotValidException", exception)
        return ResponseEntity.badRequest().body(HttpErrorResponse.of(exception.message, ErrorCode.INVALID_INPUT))
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException::class)
    protected fun handleBindException(exception: BindException): ResponseEntity<HttpErrorResponse> {
        log.error("handleBindException", exception)
        return ResponseEntity.badRequest().body(HttpErrorResponse.of(exception.message, ErrorCode.INVALID_INPUT))
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(
        exception: MethodArgumentTypeMismatchException,
    ): ResponseEntity<HttpErrorResponse> {
        log.error("handleMethodArgumentTypeMismatchException", exception)
        return ResponseEntity.badRequest().body(HttpErrorResponse.of(exception.message!!, ErrorCode.INVALID_INPUT))
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleHttpRequestMethodNotSupportedException(
        exception: HttpRequestMethodNotSupportedException,
    ): ResponseEntity<HttpErrorResponse> {
        log.error("handleHttpRequestMethodNotSupportedException", exception)
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(HttpErrorResponse.of(exception.message!!, ErrorCode.METHOD_NOT_ALLOWED))
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생
     */
    @ExceptionHandler(AccessDeniedException::class)
    protected fun handleAccessDeniedException(
        exception: AccessDeniedException,
    ): ResponseEntity<HttpErrorResponse> {
        log.error("handleAccessDeniedException", exception)
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(HttpErrorResponse.of(exception.message!!, ErrorCode.UNAUTHORIZED_USER))
    }

    /**
     * 별도로 핸들링 되지 않은 에러인 경우 발생
     */
    @ExceptionHandler(Exception::class)
    protected fun handleException(exception: Exception): ResponseEntity<HttpErrorResponse> {
        log.error("handleUnhandledException", exception)
        return ResponseEntity.internalServerError()
            .body(HttpErrorResponse.of(exception.message!!, ErrorCode.INTERNAL_SERVER_ERROR))
    }
}

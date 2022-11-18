package io.fana.cruel.app.v1.auth.presentation

import io.fana.cruel.app.v1.auth.application.LoginService
import io.fana.cruel.domain.auth.application.SignUpService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증 API", description = "Authentication API")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val signUpService: SignUpService,
    private val loginService: LoginService,
) {
    @Operation(operationId = "login", description = "로그인")
    @ApiResponse(description = "로그인 성공", responseCode = "200")
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
    ): LoginResponse {
        return loginService.login(request)
    }

    @Operation(operationId = "signUp", description = "회원가입")
    @ApiResponse(description = "회원가입 성공", responseCode = "201")
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody request: SignUpRequest,
    ): SignUpResponse {
        return SignUpResponse.of(
            signUpService.signUp(request.nickName, request.plainPassword)
        )
    }
}

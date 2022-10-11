package io.fana.cruel.app.v1.auth

import io.fana.cruel.app.security.JwtProvider
import io.fana.cruel.app.security.UserRole
import io.fana.cruel.domain.auth.application.LoginService
import io.fana.cruel.domain.auth.application.SignUpService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val signUpService: SignUpService,
    private val loginService: LoginService,
    private val jwtProvider: JwtProvider,
) {
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
    ): LoginResponse {
        val loginUser = loginService.login(request.nickName, request.plainPassword)
        val token = jwtProvider.createToken(loginUser.id, UserRole.COMMON)
        return LoginResponse(loginUser.id, loginUser.nickName, token)
    }

    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody request: SignUpRequest,
    ): SignUpResponse = SignUpResponse.of(
        signUpService.signUp(request.nickName, request.plainPassword)
    )
}

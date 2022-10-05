package io.fana.cruel.app.v1.auth

import io.fana.cruel.domain.auth.application.SignUpService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val signUpService: SignUpService,
) {
    @PostMapping("/login")
    fun login() {
        TODO()
    }

    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody request: SignUpRequest,
    ): SignUpResponse = SignUpResponse.of(
        signUpService.signUp(request.nickName, request.plainPassword)
    )
}

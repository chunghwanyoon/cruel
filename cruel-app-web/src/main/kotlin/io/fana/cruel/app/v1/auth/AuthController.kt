package io.fana.cruel.app.v1.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @PostMapping("login")
    fun login() {
        TODO()
    }

    fun signUp() {
        TODO()
    }
}

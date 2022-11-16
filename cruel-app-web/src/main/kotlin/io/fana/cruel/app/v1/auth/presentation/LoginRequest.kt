package io.fana.cruel.app.v1.auth.presentation

data class LoginRequest(
    val nickName: String,
    val plainPassword: String,
)

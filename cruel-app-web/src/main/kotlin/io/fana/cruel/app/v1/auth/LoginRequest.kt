package io.fana.cruel.app.v1.auth

data class LoginRequest(
    val nickName: String,
    val plainPassword: String,
)

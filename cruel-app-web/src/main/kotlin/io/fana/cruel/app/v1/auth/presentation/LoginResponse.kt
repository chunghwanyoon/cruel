package io.fana.cruel.app.v1.auth.presentation

data class LoginResponse(
    val id: Long,
    val nickName: String,
    val token: String,
)

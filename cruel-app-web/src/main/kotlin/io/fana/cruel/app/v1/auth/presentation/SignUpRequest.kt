package io.fana.cruel.app.v1.auth.presentation

data class SignUpRequest(
    val nickName: String,
    val plainPassword: String,
)

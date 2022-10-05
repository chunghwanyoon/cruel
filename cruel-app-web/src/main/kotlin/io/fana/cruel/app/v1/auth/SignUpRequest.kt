package io.fana.cruel.app.v1.auth

data class SignUpRequest(
    val nickName: String,
    val plainPassword: String,
)

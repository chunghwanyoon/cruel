package io.fana.cruel.app.security

data class LoginUser(
    val id: Long,
    val role: UserRole,
)

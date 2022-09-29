package io.fana.cruel.app.security

enum class LoginUserAuthHeader(
    val key: String,
) {
    USER_ID("x-user-id"),
    USER_ROLE("x-user-role")
}

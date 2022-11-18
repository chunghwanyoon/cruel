package io.fana.cruel.app.security

enum class UserRole(
    val role: String,
) {
    INTERNAL("ROLE_INTERNAL"),
    ADMIN("ROLE_ADMIN"),
    COMMON("ROLE_COMMON");

    companion object {
        fun from(role: String): UserRole = values()
            .find { role == it.role } ?: throw IllegalArgumentException("권한이 올바르지 않습니다. role=$role")
    }
}

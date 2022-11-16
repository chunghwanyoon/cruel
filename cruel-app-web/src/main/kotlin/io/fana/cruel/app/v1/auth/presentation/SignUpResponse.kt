package io.fana.cruel.app.v1.auth.presentation

import io.fana.cruel.domain.user.domain.User
import java.time.LocalDateTime

data class SignUpResponse(
    val id: Long,
    val nickName: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun of(user: User) = SignUpResponse(
            id = user.id,
            nickName = user.nickName,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
        )
    }
}

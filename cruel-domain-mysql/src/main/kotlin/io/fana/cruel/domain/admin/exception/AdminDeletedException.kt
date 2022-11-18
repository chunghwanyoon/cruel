package io.fana.cruel.domain.admin.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.UnauthorizedAccessException

class AdminDeletedException(
    message: String?,
    cause: Throwable? = null,
) : UnauthorizedAccessException(ErrorCode.UNAUTHORIZED_ADMIN, message, cause) {
    companion object {
        fun of(adminId: Long, name: String) = AdminDeletedException(
            "삭제된 어드민입니다. adminId=$adminId name=$name"
        )
    }
}

package io.fana.cruel.domain.admin.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.NotFoundException

class AdminNotFoundException(
    message: String?,
    cause: Throwable? = null,
) : NotFoundException(ErrorCode.ADMIN_NOT_FOUND, message, cause) {
    companion object {
        fun ofUserId(userId: Long) = AdminNotFoundException("Admin not found with userId: $userId")
    }
}

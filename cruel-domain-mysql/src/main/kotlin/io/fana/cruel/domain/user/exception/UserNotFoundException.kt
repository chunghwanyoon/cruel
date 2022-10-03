package io.fana.cruel.domain.user.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.NotFoundException

class UserNotFoundException(
    message: String,
    cause: Throwable? = null,
) : NotFoundException(ErrorCode.USER_NOT_FOUND, message, cause) {
    companion object {
        fun ofId(userId: Long) = UserNotFoundException("사용자를 찾을 수 없습니다. id=$userId")

        fun ofNickName(nickName: String) = UserNotFoundException("사용자를 찾을 수 없습니다. nickName=$nickName")
    }
}

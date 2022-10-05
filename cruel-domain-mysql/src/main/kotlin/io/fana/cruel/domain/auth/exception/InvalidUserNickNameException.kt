package io.fana.cruel.domain.auth.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException
import io.fana.cruel.domain.auth.application.SignUpService

class InvalidUserNickNameException(
    message: String,
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.INVALID_NICK_NAME, message, cause) {
    companion object {
        fun ofSize(): InvalidUserNickNameException {
            return InvalidUserNickNameException(
                "닉네임은 ${SignUpService.MIN_NICK_NAME_LENGTH}자 이상, ${SignUpService.MAX_NICK_NAME_LENGTH}자 이하여야 합니다"
            )
        }

        fun ofPattern(nickName: String) = InvalidUserNickNameException("올바르지 않은 닉네임 형식입니다: $nickName")
    }
}

package io.fana.cruel.domain.auth.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException
import io.fana.cruel.domain.auth.application.SignUpService

class InvalidPasswordException(
    message: String = "올바르지 않은 비밀번호 입니다.",
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.INVALID_PASSWORD, message, cause) {
    companion object {
        fun ofSize(): InvalidPasswordException {
            return InvalidPasswordException(
                "비밀번호는 ${SignUpService.MIN_PASSWORD_LENGTH}자 이상, ${SignUpService.MAX_PASSWORD_LENGTH}자 이하여야 합니다."
            )
        }
    }
}

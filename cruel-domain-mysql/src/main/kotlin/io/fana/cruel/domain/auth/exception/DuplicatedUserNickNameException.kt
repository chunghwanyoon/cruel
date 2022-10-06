package io.fana.cruel.domain.auth.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException

class DuplicatedUserNickNameException(
    message: String,
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.DUPLICATED_NICK_NAME, message, cause) {
    companion object {
        fun of(nickName: String) = DuplicatedUserNickNameException("중복된 닉네임 입니다. nickName=$nickName")
    }
}

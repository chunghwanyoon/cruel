package io.fana.cruel.domain.user.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.NotFoundException
import io.fana.cruel.core.type.LoginType

class LoginMethodNotFoundException(
    message: String,
    cause: Throwable? = null,
) : NotFoundException(ErrorCode.LOGIN_METHOD_NOT_FOUND, message, cause) {
    companion object {
        fun of(loginType: LoginType, version: Int) =
            LoginMethodNotFoundException("로그인에 실패하였습니다. loginType=$loginType version=$version")
    }
}

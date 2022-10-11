package io.fana.cruel.domain.auth.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.AuthenticationFailedException

class LoginFailedException(
    message: String? = "인증에 실패하였습니다.",
    cause: Throwable? = null,
) : AuthenticationFailedException(ErrorCode.INTERNAL_SERVER_ERROR, message, cause)

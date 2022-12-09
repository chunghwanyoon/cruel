package io.fana.cruel.domain.kyc.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException

class PhoneAuthenticationAlreadyExistException(
    message: String?,
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.PHONE_AUTHENTICATION_ALREADY_EXIST, message, cause) {
    companion object {
        fun ofUserId(userId: Long) =
            PhoneAuthenticationAlreadyExistException("이미 본인 인증이 완료된 사용자입니다. userId: $userId")

        fun ofPhoneNumber(phoneNumber: String) =
            PhoneAuthenticationAlreadyExistException("이미 인증된 휴대폰 번호입니다. phoneNumber: $phoneNumber")
    }
}

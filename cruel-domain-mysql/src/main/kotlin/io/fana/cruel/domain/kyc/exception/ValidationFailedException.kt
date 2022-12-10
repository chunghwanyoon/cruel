package io.fana.cruel.domain.kyc.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException

class ValidationFailedException(
    message: String?,
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.VALIDATION_FAILED, message, cause) {
    companion object {
        fun ofPhoneNumber(phoneNumber: String) =
            ValidationFailedException("휴대폰 번호 형식이 맞지 않습니다. 11자리 '-'를 제외하고 입력해주세요. 입력값: $phoneNumber")

        fun ofRrn() = ValidationFailedException("주민등록번호 형식이 맞지 않습니다.")

        fun ofRrnCheckSum() = ValidationFailedException("유효하지 않은 주민등록번호 입니다.")
    }
}

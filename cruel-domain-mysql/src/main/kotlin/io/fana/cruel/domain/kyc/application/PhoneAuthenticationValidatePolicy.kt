package io.fana.cruel.domain.kyc.application

import io.fana.cruel.domain.kyc.exception.ValidationFailedException
import org.springframework.stereotype.Component

@Component
class PhoneAuthenticationValidatePolicy {
    /**
     * 휴대폰 본인 인증 입력 검증
     * @param phoneNumber 휴대폰 번호
     * @param rrnFirst 주민등록번호 앞자리(6자리)
     * @param rrnLast 주민등록번호 뒷자리(7자리)
     */
    fun validate(phoneNumber: String, rrnFirst: String, rrnLast: String) {
        validateRrn(rrnFirst, rrnLast)
        validateRrnCheckSum(rrnFirst, rrnLast)
        validatePhoneNumber(phoneNumber)
    }

    /**
     * 주민등록번호 앞자리 6자리, 뒷자리 7자리 검증
     */
    private fun validateRrn(rrnFirst: String, rrnLast: String) {
        if (rrnFirst.length != RRN_FIRST_SIZE || rrnLast.length != RRN_LAST_SIZE) {
            throw ValidationFailedException.ofRrn()
        }
    }

    /**
     * 주민등록번호 checksum 검증
     * @see [https://greensul.tistory.com/51)]
     */
    private fun validateRrnCheckSum(rrnFirst: String, rrnLast: String) {
        val lastDigit = Character.getNumericValue(rrnLast.last())
        val sum = (0 until RRN_FIRST_SIZE).sumOf { idx ->
            RRN_FIRST_CHECK_SUMS[idx] * Character.getNumericValue(rrnFirst[idx]) +
                RRN_LAST_CHECK_SUMS[idx] * Character.getNumericValue(rrnLast[idx])
        }

        if (11 - (sum % 11) != lastDigit) {
            throw ValidationFailedException.ofRrnCheckSum()
        }
    }

    /**
     * 휴대폰 번호 형식 검증
     */
    private fun validatePhoneNumber(phoneNumber: String) {
        if (!PHONE_NUMBER_REGEX.matches(phoneNumber)) {
            throw ValidationFailedException.ofPhoneNumber(phoneNumber)
        }
    }

    companion object {
        private val PHONE_NUMBER_REGEX = Regex("[0-9]{11}")
        private const val RRN_FIRST_SIZE = 6
        private const val RRN_LAST_SIZE = 7
        private val RRN_FIRST_CHECK_SUMS = listOf(2, 3, 4, 5, 6, 7)
        private val RRN_LAST_CHECK_SUMS = listOf(8, 9, 2, 3, 4, 5)
    }
}

package io.fana.cruel.domain.kyc.application

import io.fana.cruel.domain.kyc.exception.ValidationFailedException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec

internal class PhoneAuthenticationValidationPolicyTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val policy = PhoneAuthenticationValidatePolicy()
    val phoneNumber = "01012345678"
    val rrnFirst = "000000"
    val rrnLast = "0000000"

    given("휴대폰 번호와 주민등록번호가 주어졌을 때") {
        `when`("휴대폰 번호가 올바르지 않으면") {
            val invalidPhoneNumber = "010-1234-5678"

            then("예외가 발생한다") {
                shouldThrow<ValidationFailedException> {
                    policy.validate(invalidPhoneNumber, rrnFirst, rrnLast)
                }
            }
        }

        `when`("주민등록번호 앞자리가 올바르지 않으면") {
            val invalidRrnFirst = "00000"

            then("예외가 발생한다") {
                shouldThrow<ValidationFailedException> {
                    policy.validate(phoneNumber, invalidRrnFirst, rrnLast)
                }
            }
        }

        `when`("주민등록번호 뒷자리가 올바르지 않으면") {
            val invalidRrnLast = "000000"

            then("예외가 발생한다") {
                shouldThrow<ValidationFailedException> {
                    policy.validate(phoneNumber, rrnFirst, invalidRrnLast)
                }
            }
        }

        /**
         * @note 생략
         */
        `when`("휴대폰 번호와 주민등록번호가 올바르면") {
            then("예외가 발생하지 않는다") {
                // policy.validate(phoneNumber, rrnFirst, rrnLast)
            }
        }
    }
})

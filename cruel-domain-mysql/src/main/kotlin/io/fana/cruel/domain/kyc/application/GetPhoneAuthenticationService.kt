package io.fana.cruel.domain.kyc.application

import io.fana.cruel.domain.kyc.domain.PhoneAuthentication
import io.fana.cruel.domain.kyc.domain.PhoneAuthenticationRepository
import io.fana.cruel.domain.kyc.exception.PhoneAuthenticationNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class GetPhoneAuthenticationService(
    private val phoneAuthenticationRepository: PhoneAuthenticationRepository,
) {
    fun isPhoneAuthenticationVerified(userId: Long): Boolean {
        val auth = findPhoneAuthenticationByUserId(userId)
        return auth?.isAuthenticated() ?: false
    }

    fun getPhoneAuthenticationByPhoneNumber(phoneNumber: String): PhoneAuthentication =
        findPhoneAuthenticationByPhoneNumber(phoneNumber) ?: throw PhoneAuthenticationNotFoundException()

    fun getPhoneAuthenticationByUserId(userId: Long): PhoneAuthentication =
        findPhoneAuthenticationByUserId(userId) ?: throw PhoneAuthenticationNotFoundException()

    fun findPhoneAuthenticationByUserId(userId: Long): PhoneAuthentication? =
        phoneAuthenticationRepository.findByUserId(userId)

    fun findPhoneAuthenticationByPhoneNumber(phoneNumber: String): PhoneAuthentication? =
        phoneAuthenticationRepository.findByPhoneNumber(phoneNumber)
}

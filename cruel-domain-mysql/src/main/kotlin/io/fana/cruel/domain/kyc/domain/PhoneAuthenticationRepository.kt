package io.fana.cruel.domain.kyc.domain

import org.springframework.stereotype.Repository

@Repository
interface PhoneAuthenticationRepository {
    fun findByUserId(userId: Long): PhoneAuthentication?

    fun findByPhoneNumber(phoneNumber: String): PhoneAuthentication?

    fun save(phoneAuthentication: PhoneAuthentication): PhoneAuthentication
}

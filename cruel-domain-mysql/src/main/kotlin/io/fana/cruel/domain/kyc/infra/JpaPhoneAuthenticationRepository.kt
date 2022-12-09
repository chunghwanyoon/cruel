package io.fana.cruel.domain.kyc.infra

import io.fana.cruel.domain.kyc.domain.PhoneAuthentication
import io.fana.cruel.domain.kyc.domain.PhoneAuthenticationRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPhoneAuthenticationRepository : PhoneAuthenticationRepository, JpaRepository<PhoneAuthentication, Long> {
    override fun findByUserId(userId: Long): PhoneAuthentication?

    override fun findByPhoneNumber(phoneNumber: String): PhoneAuthentication?

    override fun save(phoneAuthentication: PhoneAuthentication): PhoneAuthentication
}

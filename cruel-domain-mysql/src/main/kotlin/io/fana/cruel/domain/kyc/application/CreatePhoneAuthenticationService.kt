package io.fana.cruel.domain.kyc.application

import io.fana.cruel.domain.kyc.domain.PhoneAuthentication
import io.fana.cruel.domain.kyc.domain.PhoneAuthenticationRepository
import io.fana.cruel.domain.kyc.exception.PhoneAuthenticationAlreadyExistException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CreatePhoneAuthenticationService(
    private val phoneAuthenticationRepository: PhoneAuthenticationRepository,
    private val phoneAuthenticationValidatePolicy: PhoneAuthenticationValidatePolicy,
    private val getPhoneAuthenticationService: GetPhoneAuthenticationService,
) {
    fun createPhoneAuth(userId: Long, request: PhoneAuthenticationRequest): PhoneAuthentication {
        phoneAuthenticationValidatePolicy.validate(request.phoneNumber, request.rrnFirst, request.rrnLast)
        requirePhoneAuthenticationNotExists(userId)
        requireUniquePhoneNumber(request.phoneNumber)
        var auth = getPhoneAuthenticationService.findPhoneAuthenticationByUserId(userId)

        auth = auth?.updateUnverified(request.phoneNumber, request.mobileCode)
            ?: phoneAuthenticationRepository.save(
                PhoneAuthentication(
                    userId = userId,
                    name = request.name,
                    unverifiedPhoneNumber = request.phoneNumber,
                    unverifiedMobileCode = request.mobileCode,
                )
            )
        // TODO: request to external 3rd authenticate API
        return auth
    }

    private fun requirePhoneAuthenticationNotExists(userId: Long) {
        val auth = getPhoneAuthenticationService.findPhoneAuthenticationByUserId(userId)
        if (auth != null && auth.isAuthenticated()) {
            throw PhoneAuthenticationAlreadyExistException.ofUserId(userId)
        }
    }

    private fun requireUniquePhoneNumber(phoneNumber: String) {
        val auth = getPhoneAuthenticationService.findPhoneAuthenticationByPhoneNumber(phoneNumber)
        if (auth != null) {
            throw PhoneAuthenticationAlreadyExistException.ofPhoneNumber(phoneNumber)
        }
    }
}

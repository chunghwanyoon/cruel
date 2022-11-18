package io.fana.cruel.domain.admin.application

import io.fana.cruel.domain.admin.domain.Admin
import io.fana.cruel.domain.admin.domain.AdminRepository
import io.fana.cruel.domain.admin.exception.AdminNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class GetAdminService(
    private val adminRepository: AdminRepository,
) {
    fun getAdminByUserId(userId: Long): Admin {
        return adminRepository.findAdminByUserId(userId) ?: throw AdminNotFoundException.ofUserId(userId)
    }
}

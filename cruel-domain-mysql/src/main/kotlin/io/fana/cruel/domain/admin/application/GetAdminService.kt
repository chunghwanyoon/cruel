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
    fun getAdminById(adminId: Long): Admin {
        return adminRepository.findAdminById(adminId) ?: throw AdminNotFoundException.ofId(adminId)
    }
}

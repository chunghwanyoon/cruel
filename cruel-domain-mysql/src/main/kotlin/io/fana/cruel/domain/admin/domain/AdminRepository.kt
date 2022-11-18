package io.fana.cruel.domain.admin.domain

import org.springframework.stereotype.Repository

@Repository
interface AdminRepository {
    fun findAdminByUserId(userId: Long): Admin?

    fun save(): Admin
}

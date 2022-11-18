package io.fana.cruel.domain.admin.domain

import org.springframework.stereotype.Repository

@Repository
interface AdminRepository {
    fun findAdminById(id: Long): Admin?

    fun save(): Admin
}

package io.fana.cruel.domain.admin.infra

import io.fana.cruel.domain.admin.domain.Admin
import io.fana.cruel.domain.admin.domain.AdminRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaAdminRepository : AdminRepository, JpaRepository<Admin, Long> {
    override fun findAdminByUserId(id: Long): Admin?

    override fun save(): Admin
}

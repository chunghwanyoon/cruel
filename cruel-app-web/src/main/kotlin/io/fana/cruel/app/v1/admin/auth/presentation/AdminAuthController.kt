package io.fana.cruel.app.v1.admin.auth.presentation

import io.fana.cruel.domain.admin.application.GetAdminService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin/auth")
class AdminAuthController(
    private val getAdminService: GetAdminService,
) {
    fun loginAdmin() {
        TODO()
    }

    fun registerAdmin() {
        TODO()
    }
}

package io.fana.cruel.domain.admin.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.admin.domain.Admin
import io.fana.cruel.domain.admin.domain.AdminRepository
import io.fana.cruel.domain.admin.exception.AdminNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class GetAdminServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val adminId = 1L
    val adminFixture = fixture<Admin> {
        property(Admin::id) { adminId }
    }
    val adminRepository = mockk<AdminRepository>()
    val getAdminService = GetAdminService(
        adminRepository = adminRepository
    )

    given("어드민이 주어졌을 때") {
        `when`("어드민을 조회하면") {
            every { adminRepository.findAdminByUserId(adminId) } returns adminFixture

            then("어드민이 조회된다") {
                val admin = getAdminService.getAdminByUserId(adminId)

                admin.id shouldBe adminId
                admin.deletedAt shouldBe null
            }
        }

        `when`("어드민이 존재하지 않으면") {
            every { adminRepository.findAdminByUserId(adminId) } returns null

            then("예외가 발생한다") {
                shouldThrow<AdminNotFoundException> {
                    getAdminService.getAdminByUserId(adminId)
                }
            }
        }
    }
})

package io.fana.cruel.domain.user.application

import com.navercorp.fixturemonkey.FixtureMonkey
import io.fana.cruel.core.entity.user.User
import io.fana.cruel.domain.user.domain.UserRepository
import io.fana.cruel.domain.user.exception.UserNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class GetUserServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val userRepository = mockk<UserRepository>()
    val getUserService = GetUserService(
        userRepository = userRepository
    )
    val userId = 1L
    val userNickName = "Cruel"
    val userFixture = FixtureMonkey.create()
        .giveMeBuilder(User::class.java)
        .set("nickName", userNickName)
        .set("id", userId)
        .sample()

    given("유저가 주어졌을 때") {
        `when`("유저 아이디가 주어지면") {
            every { userRepository.findUserById(userId) } returns userFixture

            then("유저를 조회할 수 있다") {
                val user = getUserService.getUserById(userId)
                user.id shouldBe userFixture.id
                user.nickName shouldBe userFixture.nickName
            }
        }

        `when`("유저 닉네임이 주어지면") {
            every { userRepository.findUserByNickName(userNickName) } returns userFixture

            then("유저를 조회할 수 있다") {
                val user = getUserService.getUserByNickName(userNickName)
                user.id shouldBe userFixture.id
                user.nickName shouldBe userFixture.nickName
            }
        }

        `when`("유저가 아이디가 존재하지 않으면") {
            every { userRepository.findUserById(userId) } returns null

            then("예외가 발생한다") {
                shouldThrow<UserNotFoundException> {
                    getUserService.getUserById(userId)
                }
            }
        }

        `when`("유저 닉네임이 존재하지 않으면") {
            every { userRepository.findUserByNickName(userNickName) } returns null

            then("예외가 발생한다") {
                shouldThrow<UserNotFoundException> {
                    getUserService.getUserByNickName(userNickName)
                }
            }
        }
    }
})

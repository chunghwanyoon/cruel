package io.fana.cruel.app.v1.auth.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.app.security.JwtProvider
import io.fana.cruel.app.security.UserRole
import io.fana.cruel.app.v1.auth.presentation.LoginRequest
import io.fana.cruel.core.type.LoginType
import io.fana.cruel.domain.auth.domain.LoginMethod
import io.fana.cruel.domain.auth.exception.LoginFailedException
import io.fana.cruel.domain.crypt.application.CryptService
import io.fana.cruel.domain.user.application.GetUserService
import io.fana.cruel.domain.user.domain.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class LoginServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val getUserService = mockk<GetUserService>()
    val cryptService = mockk<CryptService>()
    val jwtProvider = mockk<JwtProvider>()

    val loginService = LoginService(
        getUserService = getUserService,
        cryptService = cryptService,
        jwtProvider = jwtProvider,
    )
    val fixture = kotlinFixture()
    val nickName = "FANA"
    val plainPassword = "PLAIN_PASSWORD"
    val hashedPassword = "HASHED_PASSWORD"
    val randomSalt = "RANDOM_SALT"
    val loginMethodFixture = fixture<LoginMethod> {
        property(LoginMethod::loginType) { LoginType.PASSWORD }
        property(LoginMethod::randomSalt) { randomSalt }
        property(LoginMethod::hashedValue) { hashedPassword }
        property(LoginMethod::version) { 1 }
    }
    val userFixture = fixture<User> {
        property(User::nickName) { nickName }
        property(User::loginMethods) { mutableListOf(loginMethodFixture) }
    }
    val loginRequest = LoginRequest(nickName, plainPassword)

    given("로그인 정보가 주어졌을 때") {
        every { getUserService.getUserByNickName(nickName) } returns userFixture
        every { jwtProvider.createToken(userFixture.id, UserRole.COMMON) } returns "TOKEN"

        `when`("로그인을 시도하면") {
            every { cryptService.encrypt(plainPassword, randomSalt) } returns hashedPassword

            then("로그인 성공한다") {
                val response = loginService.login(loginRequest)

                response.id shouldBe userFixture.id
                response.nickName shouldBe nickName
            }
        }

        `when`("비밀번호가 다르면") {
            every { cryptService.encrypt(plainPassword, randomSalt) } returns "WRONG_PASSWORD"

            then("예외가 발생한다") {
                shouldThrow<LoginFailedException> {
                    loginService.login(loginRequest)
                }
            }
        }
    }
})

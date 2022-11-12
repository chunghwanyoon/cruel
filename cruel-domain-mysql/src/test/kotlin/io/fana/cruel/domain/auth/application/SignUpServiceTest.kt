package io.fana.cruel.domain.auth.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.core.type.LoginType
import io.fana.cruel.domain.auth.domain.LoginMethod
import io.fana.cruel.domain.auth.exception.DuplicatedUserNickNameException
import io.fana.cruel.domain.auth.exception.InvalidPasswordException
import io.fana.cruel.domain.auth.exception.InvalidUserNickNameException
import io.fana.cruel.domain.crypt.application.CryptService
import io.fana.cruel.domain.user.application.CreateUserService
import io.fana.cruel.domain.user.application.GetUserService
import io.fana.cruel.domain.user.domain.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class SignUpServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val getUserService = mockk<GetUserService>()
    val createUserService = mockk<CreateUserService>()
    val cryptService = mockk<CryptService>()

    val signUpService = SignUpService(
        getUserService = getUserService,
        createUserService = createUserService,
        cryptService = cryptService,
    )
    val nickName = "FANA"
    val plainPassword = "PLAIN_PASSWORD"
    val hashedPassword = "HASHED_PASSWORD"
    val fixture = kotlinFixture()
    val loginMethodFixture = fixture<LoginMethod> {
        property(LoginMethod::loginType) { LoginType.PASSWORD }
        property(LoginMethod::version) { 1 }
        property(LoginMethod::hashedValue) { hashedPassword }
    }
    val userFixture = fixture<User> {
        property(User::loginMethods) { mutableListOf(loginMethodFixture) }
    }

    given("닉네임과 패스워드가 주어졌을 때") {
        every { getUserService.findUserByNickName(any()) } returns null
        every { createUserService.createUser(nickName) } returns userFixture
        every { cryptService.encrypt(any(), any()) } returns hashedPassword

        `when`("회원가입을 시도하면") {
            then("회원가입이 된다") {
                val user = signUpService.signUp(nickName, plainPassword)
                val loginMethod = user.loginMethods.find { it.loginType === LoginType.PASSWORD && it.version == 1 }

                user.nickName shouldBe userFixture.nickName
                loginMethod!!.hashedValue shouldBe hashedPassword
            }
        }

        // validations
        `when`("닉네임이 이미 존재하면") {
            every { getUserService.findUserByNickName(nickName) } returns userFixture
            then("예외가 발생한다") {
                shouldThrow<DuplicatedUserNickNameException> {
                    signUpService.signUp(nickName, plainPassword)
                }
            }
        }

        `when`("닉네임 길이가 유효하지 않으면") {
            then("예외가 발생한다") {
                shouldThrow<InvalidUserNickNameException> {
                    signUpService.signUp(
                        nickName = "thisIsTooooooooooooooooooooooooooooooooooooooooooooooooooooooooLongForNickName",
                        plainPassword = plainPassword
                    )
                }
            }
        }

        `when`("닉네임 패턴이 유효하지 않으면") {
            then("예외가 발생한다") {
                shouldThrow<InvalidUserNickNameException> {
                    signUpService.signUp(
                        nickName = "!@#%@$$%&$%&$%&$%&$&%$&$%&",
                        plainPassword = plainPassword
                    )
                }
            }
        }

        `when`("패스워드 길이가 유효하지 않으면") {
            then("예외가 발생한다") {
                shouldThrow<InvalidPasswordException> {
                    signUpService.signUp(
                        nickName = nickName,
                        plainPassword = "thisIsTooooooooooooooooooooooooooooooooooooooooooooooooooooooooLongForPassword"
                    )
                }
            }
        }
    }
})

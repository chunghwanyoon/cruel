package io.fana.cruel.domain.crypt.application

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldNotBeEqualComparingTo

internal class CryptServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val cryptService = CryptService("TestSecretKey")
    val data = "DATA"

    given("encrypt할 데이터가 주어졌을 때") {
        `when`("encrypt를 시도하면") {
            then("encrypt가 된다") {
                val hashedValue = cryptService.encrypt(data, "salt")
                hashedValue shouldNotBeEqualComparingTo data
            }
        }
    }
})

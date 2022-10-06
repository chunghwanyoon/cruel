package io.fana.cruel.domain.util

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class RandomUtilTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val size = 16

    given("사이즈가 주어졌을 때") {
        `when`("랜덤 alpha numeric을 요청하였을 때") {
            then("주어진 사이즈만큼의 alpha numeric이 반환된다") {
                val randomAlphaNumeric = randomAlphaNumeric(size)
                randomAlphaNumeric.length shouldBe size
            }
        }
    }
})

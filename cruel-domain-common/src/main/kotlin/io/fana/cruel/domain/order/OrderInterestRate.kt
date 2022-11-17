package io.fana.cruel.domain.order

import java.math.BigDecimal

data class OrderInterestRate(
    val rate: BigDecimal = BigDecimal.valueOf(0.1),
) {
    init {
        // FIXME: temp implementation
        require(rate >= BigDecimal.ZERO) { "rate must be greater than or equal to 0" }
    }
}

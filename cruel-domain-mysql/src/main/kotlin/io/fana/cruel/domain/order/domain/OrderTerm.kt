package io.fana.cruel.domain.order.domain

import io.fana.cruel.domain.order.exception.InvalidOrderTermException

data class OrderTerm(
    val term: Int,
) {
    init {
        requireValidOrderTerm()
    }

    private fun requireValidOrderTerm() {
        if (term > MAX_TERM || term < MIN_TERM) {
            throw InvalidOrderTermException.of(MIN_TERM, MAX_TERM)
        }
    }

    companion object {
        private const val MAX_TERM = 12
        private const val MIN_TERM = 3
    }
}

package io.fana.cruel.core.exception

enum class ErrorGroup(
    val prefix: String,
) {
    COMMON("01"),
    AUTH("02"),
    USER("03"),
    ORDER("04"),
    ADMIN("05"),
    REPAYMENT("06");
}

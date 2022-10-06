package io.fana.cruel.domain.util

fun randomAlphaNumeric(size: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..size)
        .map { allowedChars.random() }
        .joinToString("")
}

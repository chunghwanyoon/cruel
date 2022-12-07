package io.fana.cruel.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun convertToLocalDate(dateString: String): LocalDate =
    LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

fun LocalDate.toLocalDateTime() = this.atStartOfDay()

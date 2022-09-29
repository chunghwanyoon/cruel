package io.fana.cruel.app.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

internal inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

package io.fana.cruel.app.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "cruel.jwt")
data class JwtSecretKeyProperties(
    val secret: String,
    val expirySeconds: Int,
)

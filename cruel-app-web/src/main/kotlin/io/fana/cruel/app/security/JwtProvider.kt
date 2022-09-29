package io.fana.cruel.app.security

import io.fana.cruel.app.config.JwtSecretKeyProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.EnableConfigurationProperties
import java.nio.charset.StandardCharsets
import java.util.Date

@EnableConfigurationProperties(
    value = [
        JwtSecretKeyProperties::class
    ]
)
class JwtProvider(
    private val jwtProperties: JwtSecretKeyProperties,
) {
    private val signingKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

    fun createToken(id: Long, role: UserRole): String {
        val now = Date()

        return Jwts.builder()
            .setClaims(
                mutableMapOf(
                    USER_ID_CLAIM_KEY to id,
                    USER_ROLE_CLAIM_KEY to role.role
                )
            )
            .setIssuedAt(now)
            .setExpiration(Date(now.time + (jwtProperties.expirySeconds * 1000)))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()
    }

    companion object {
        private const val USER_ID_CLAIM_KEY = "id"
        private const val USER_ROLE_CLAIM_KEY = "role"
    }
}

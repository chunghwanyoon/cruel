package io.fana.cruel.app.security

import io.fana.cruel.app.config.JwtSecretKeyProperties
import io.fana.cruel.app.security.exception.InvalidJwtException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date

@EnableConfigurationProperties(
    value = [
        JwtSecretKeyProperties::class
    ]
)
@Component
class JwtProvider(
    private val jwtProperties: JwtSecretKeyProperties,
) {
    private val signingKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

    fun createToken(userId: Long, role: UserRole): String {
        val now = Date()

        return Jwts.builder()
            .setClaims(
                mutableMapOf(
                    USER_ID_CLAIM_KEY to userId,
                    USER_ROLE_CLAIM_KEY to role.role
                )
            )
            .setIssuedAt(now)
            .setExpiration(Date(now.time + (jwtProperties.expirySeconds * 1000)))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractPayload(token: String): UserPrincipal {
        try {
            val claims = getClaims(token.substringAfter(AUTHORIZATION_TYPE + TOKEN_DELIMITER))
            val userId = claims.get(USER_ID_CLAIM_KEY, Integer::class.java).toLong()
            val role = claims.get(USER_ROLE_CLAIM_KEY, String::class.java)
            return fromToken(userId, UserRole.from(role))
        } catch (exception: Exception) {
            throw InvalidJwtException()
        }
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    companion object {
        private const val AUTHORIZATION_TYPE = "Bearer"
        private const val TOKEN_DELIMITER = " "
        private const val USER_ID_CLAIM_KEY = "userId"
        private const val USER_ROLE_CLAIM_KEY = "role"
    }
}

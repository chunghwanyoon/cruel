package io.fana.cruel.domain.crypt.application

import org.springframework.stereotype.Component
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Component
class CryptService(
    // @Value("#{cruel.secret-key}")
    private val secretKey: String = "ThisIsTempSecretKey",
) {
    fun encrypt(data: String, salt: String? = ""): String {
        val sha256Hmac = Mac.getInstance(ALGORITHM)
        val key = SecretKeySpec(secretKey.toByteArray(), ALGORITHM)
        sha256Hmac.init(key)
        val target = data + salt
        return Base64.getEncoder().encodeToString(sha256Hmac.doFinal(target.toByteArray()))
    }

    companion object {
        const val ALGORITHM = "HmacSHA256"
    }
}

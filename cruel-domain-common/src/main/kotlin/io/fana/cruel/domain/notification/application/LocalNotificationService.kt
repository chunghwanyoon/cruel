package io.fana.cruel.domain.notification.application

import io.fana.cruel.domain.notification.domain.NotificationService
import io.fana.cruel.domain.util.logger
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile(value = ["local"])
@Component
class LocalNotificationService : NotificationService {
    private val log = logger()

    override fun sendSMS(phoneNumber: String, message: String) {
        log.info("############### SENDING SMS NOTIFICATION ###############")
        log.info("Sending SMS to $phoneNumber: $message")
    }
}

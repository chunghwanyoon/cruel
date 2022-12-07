package io.fana.cruel.domain.notification.domain

interface NotificationService {
    fun sendSMS(phoneNumber: String, message: String)
}

package io.haerong22.ticketing.domain.reservation

data class PaymentCompletedEvent(
    val reservationId: Long,
    val paymentId: Long,
)

package io.haerong22.ticketing.domain.reservation

interface ReservationEventPublisher {

    fun publish(event: PaymentCompletedEvent)
}

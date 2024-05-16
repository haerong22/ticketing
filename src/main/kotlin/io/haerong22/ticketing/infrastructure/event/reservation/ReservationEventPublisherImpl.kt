package io.haerong22.ticketing.infrastructure.event.reservation

import io.haerong22.ticketing.domain.reservation.PaymentCompletedEvent
import io.haerong22.ticketing.domain.reservation.ReservationEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class ReservationEventPublisherImpl(
    private val publisher: ApplicationEventPublisher,
) : ReservationEventPublisher {

    override fun publish(event: PaymentCompletedEvent) {
        publisher.publishEvent(event)
    }
}

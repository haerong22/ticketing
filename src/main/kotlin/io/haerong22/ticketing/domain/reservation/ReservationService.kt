package io.haerong22.ticketing.domain.reservation

import io.haerong22.ticketing.domain.user.User
import org.springframework.stereotype.Service

@Service
class ReservationService(
    private val reservationReader: ReservationReader,
    private val reservationStore: ReservationStore,
    private val paymentValidator: PaymentValidator,
) {

    fun getReservationWithLock(reservationId: Long) : Reservation {
        return reservationReader.getReservationWithLock(reservationId)
    }

    fun pay(user: User, reservation: Reservation) {
        paymentValidator.validate(user, reservation)

        val paymentCompleted = reservation.paymentComplete()
        reservationStore.save(paymentCompleted)

        val payment = Payment.pay(reservation.reservationId, reservation.price)
        reservationStore.save(payment)
    }

    fun cancelExpiredReservation() : List<Long> {
        val reservations = reservationReader.getExpiredReservation()
        val reservationIds = reservations.map { it.reservationId }
        reservationStore.cancelExpiredReservation(reservationIds)
        return reservations.map { it.seatId }
    }
}
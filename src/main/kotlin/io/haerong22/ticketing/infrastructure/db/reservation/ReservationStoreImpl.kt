package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.reservation.Payment
import io.haerong22.ticketing.domain.reservation.Reservation
import io.haerong22.ticketing.domain.reservation.ReservationStore
import org.springframework.stereotype.Repository

@Repository
class ReservationStoreImpl(
    private val reservationJpaRepository: ReservationJpaRepository,
    private val paymentJpaRepository: PaymentJpaRepository,
) : ReservationStore {

    override fun save(reservation: Reservation): Reservation {
        val reservationEntity = ReservationEntity.of(reservation)
        return reservationJpaRepository.save(reservationEntity).toDomain()
    }

    override fun save(payment: Payment): Payment {
        val paymentEntity = PaymentEntity.of(payment)
        return paymentJpaRepository.save(paymentEntity).toDomain()
    }
}
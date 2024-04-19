package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.domain.reservation.Reservation
import io.haerong22.ticketing.domain.reservation.ReservationException
import io.haerong22.ticketing.domain.reservation.ReservationReader
import io.haerong22.ticketing.domain.reservation.ReservationResponseCode.RESERVATION_NOT_FOUND
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ReservationReaderImpl(
    private val reservationJpaRepository: ReservationJpaRepository,
) : ReservationReader {

    override fun getReservationWithLock(reservationId: Long): Reservation {
        return reservationJpaRepository.findByIdForUpdate(reservationId)
            .orElseThrow { throw ReservationException(RESERVATION_NOT_FOUND) }
            .toDomain()
    }

    override fun getExpiredReservation(): List<Reservation> {
        val status = ReservationStatus.RESERVED
        val date = LocalDateTime.now()
        return reservationJpaRepository.findAllByStatusAndExpiredAtBefore(status, date)
            .map { it.toDomain() }
    }
}
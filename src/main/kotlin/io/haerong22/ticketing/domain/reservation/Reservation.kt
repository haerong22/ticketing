package io.haerong22.ticketing.domain.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import java.time.LocalDateTime

data class Reservation(
    val userId: Long,
    val seatId: Long,
    val status: ReservationStatus,
    val expiredAt: LocalDateTime,
) {

    companion object {

        fun reserve(userId: Long, seatId: Long) : Reservation {
            return Reservation(
                userId = userId,
                seatId = seatId,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now().plusMinutes(5)
            )
        }
    }
}
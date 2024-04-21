package io.haerong22.ticketing.domain.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import java.time.LocalDateTime

data class Reservation(
    val reservationId: Long = 0,
    val userId: Long,
    val seatId: Long,
    val price: Int,
    val status: ReservationStatus,
    val expiredAt: LocalDateTime,
) {

    companion object {

        fun reserve(userId: Long, seatId: Long, price: Int): Reservation {
            return Reservation(
                userId = userId,
                seatId = seatId,
                price = price,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now().plusMinutes(5)
            )
        }
    }

    fun paymentComplete(): Reservation {
        return this.copy(status = ReservationStatus.COMPLETE)
    }
}

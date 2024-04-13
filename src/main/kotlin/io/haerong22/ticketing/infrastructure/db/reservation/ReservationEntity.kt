package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.domain.reservation.Reservation
import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "reservation")
class ReservationEntity(
    val userId: Long,
    val seatId: Long,
    val status: ReservationStatus,
    val expiredAt: LocalDateTime,
) : BaseEntity() {

    companion object {

        fun of(reservation: Reservation): ReservationEntity {
            return ReservationEntity(
                userId = reservation.userId,
                seatId = reservation.seatId,
                status = reservation.status,
                expiredAt = reservation.expiredAt,
            ).apply { id = reservation.reservationId }
        }
    }

    fun toDomain() : Reservation {
        return Reservation(
            reservationId = id!!,
            userId = userId,
            seatId = seatId,
            status = status,
            expiredAt = expiredAt,
        )
    }
}
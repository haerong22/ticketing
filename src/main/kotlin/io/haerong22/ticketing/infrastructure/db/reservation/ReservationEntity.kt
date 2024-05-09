package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.domain.reservation.Reservation
import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.LocalDateTime

@Entity
@Table(name = "reservation")
class ReservationEntity(
    val userId: Long,
    val seatId: Long,
    val price: Int,

    @Enumerated(EnumType.STRING)
    val status: ReservationStatus,
    val expiredAt: LocalDateTime,
) : BaseEntity() {

    @Version
    var version: Long = 0

    companion object {

        fun of(reservation: Reservation): ReservationEntity {
            return ReservationEntity(
                userId = reservation.userId,
                seatId = reservation.seatId,
                price = reservation.price,
                status = reservation.status,
                expiredAt = reservation.expiredAt,
            ).apply { id = reservation.reservationId }
        }
    }

    fun toDomain(): Reservation {
        return Reservation(
            reservationId = id!!,
            userId = userId,
            seatId = seatId,
            price = price,
            status = status,
            expiredAt = expiredAt,
        )
    }
}

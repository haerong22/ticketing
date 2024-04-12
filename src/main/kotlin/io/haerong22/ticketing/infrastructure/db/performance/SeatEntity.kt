package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.Seat
import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "seat")
class SeatEntity(
    val performanceScheduleId: Long,
    val seatNo: Int,
    val price: Int,

    @Enumerated(EnumType.STRING)
    val status: SeatStatus,
) : BaseEntity() {

    fun toDomain() : Seat {
        return Seat(
            seatId = id!!,
            seatNo = seatNo,
            price = price,
        )
    }
}
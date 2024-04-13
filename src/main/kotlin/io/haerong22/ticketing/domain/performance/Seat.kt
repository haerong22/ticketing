package io.haerong22.ticketing.domain.performance

import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.PerformanceResponseCode.ALREADY_RESERVED

class Seat(
    val seatId: Long = 0,
    val performanceScheduleId: Long,
    val seatNo: Int,
    val price: Int,
    val status: SeatStatus,
) {
    fun reserve(): Seat {
        if (status == SeatStatus.RESERVED) throw PerformanceException(ALREADY_RESERVED)

        return Seat(
            performanceScheduleId = performanceScheduleId,
            seatId = seatId,
            seatNo = seatNo,
            price = price,
            status = SeatStatus.RESERVED
        )
    }
}
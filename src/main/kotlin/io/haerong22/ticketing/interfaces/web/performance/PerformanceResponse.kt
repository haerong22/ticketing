package io.haerong22.ticketing.interfaces.web.performance

import io.haerong22.ticketing.domain.common.PageInfo
import java.time.LocalDateTime

class PerformanceResponse {

    class AvailableSeatList(
        val availableSeat: List<Seat>,
        val totalSeats: Int,
        val remainingSeats: Int,
    ) {

        class Seat(
            val seatId: Long,
            val seatNo: Int,
            val price: Int,
        )
    }

    class PerformanceList(
        val performances: List<Performance>,
        val page: PageInfo,
    ) {

        class Performance(
            val performanceId: Long,
            val title: String,
            val content: String,
        ) {

            companion object {

                fun toResponse(performance: io.haerong22.ticketing.domain.performance.Performance) : Performance {
                    return Performance(
                        performanceId = performance.performanceId,
                        title = performance.title,
                        content = performance.content,
                    )
                }
            }
        }
    }

    class PerformanceScheduleList(
        val title: String,
        val content: String,
        val date: List<Schedule>,
    ) {

        class Schedule(
            val performanceScheduleId: Long,
            val reservationAt: LocalDateTime,
            val startAt: LocalDateTime,
            val endAt: LocalDateTime,
        )
    }

    class ReserveSeat(
        val reservationId: Long,
    ) {
    }
}
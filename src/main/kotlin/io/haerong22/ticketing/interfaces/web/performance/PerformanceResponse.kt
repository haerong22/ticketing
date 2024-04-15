package io.haerong22.ticketing.interfaces.web.performance

import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.performance.Performance
import io.haerong22.ticketing.domain.performance.Seat
import java.time.LocalDateTime

class PerformanceResponse {

    class AvailableSeatList(
        val availableSeat: List<SeatInfo>,
    ) {

        class SeatInfo(
            val seatId: Long,
            val seatNo: Int,
            val price: Int,
        )

        companion object {

            fun toResponse(seats: List<Seat>) : AvailableSeatList {
                return AvailableSeatList(
                    availableSeat = seats.map {
                        SeatInfo(
                            seatId = it.seatId,
                            seatNo = it.seatNo,
                            price = it.price,
                        )
                    }
                )
            }
        }

    }

    class PerformanceList(
        val performances: List<PerformanceInfo>,
        val page: PageInfo,
    ) {

        class PerformanceInfo(
            val performanceId: Long,
            val title: String,
            val content: String,
        )

        companion object {

            fun toResponse(result: WithPage<Performance>): PerformanceList {
                return PerformanceList(
                    result.list.map {
                        PerformanceInfo(
                            performanceId = it.performanceId,
                            title = it.title,
                            content = it.content,
                        )
                    },
                    result.pageInfo
                )
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

        companion object {

            fun toResponse(performance: Performance): PerformanceScheduleList {
                return PerformanceResponse.PerformanceScheduleList(
                    title = performance.title,
                    content = performance.content,
                    date = performance.schedules?.map {
                        Schedule(
                            performanceScheduleId = it.performanceScheduleId,
                            reservationAt = it.reservationAt,
                            startAt = it.startAt,
                            endAt = it.endAt,
                        )
                    } ?: emptyList()
                )
            }
        }
    }


}
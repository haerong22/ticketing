package io.haerong22.ticketing.interfaces.web.performance

import io.haerong22.ticketing.domain.performance.Performance
import org.springframework.stereotype.Component

@Component
class PerformanceResponseMapper {

    fun toResponse(performance: Performance): PerformanceResponse.PerformanceScheduleList {
        return PerformanceResponse.PerformanceScheduleList(
            title = performance.title,
            content = performance.content,
            date = performance.schedules?.map {
                PerformanceResponse.PerformanceScheduleList.Schedule(
                    performanceScheduleId = it.performanceScheduleId,
                    reservationAt = it.reservationAt,
                    startAt = it.startAt,
                    endAt = it.endAt,
                )
            }?: emptyList()
        )
    }
}
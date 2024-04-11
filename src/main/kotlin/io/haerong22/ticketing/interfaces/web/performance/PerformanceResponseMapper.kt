package io.haerong22.ticketing.interfaces.web.performance

import io.haerong22.ticketing.domain.performance.Performance
import io.haerong22.ticketing.interfaces.web.performance.response.PerformanceScheduleListResponse
import org.springframework.stereotype.Component

@Component
class PerformanceResponseMapper {

    fun toResponse(performance: Performance): PerformanceScheduleListResponse {
        return PerformanceScheduleListResponse(
            title = performance.title,
            content = performance.content,
            date = performance.schedules?.map {
                PerformanceScheduleListResponse.Schedule(
                    performanceScheduleId = it.performanceScheduleId,
                    reservationAt = it.reservationAt,
                    startAt = it.startAt,
                    endAt = it.endAt,
                )
            }?: emptyList()
        )
    }
}
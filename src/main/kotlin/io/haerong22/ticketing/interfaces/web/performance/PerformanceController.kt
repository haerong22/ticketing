package io.haerong22.ticketing.interfaces.web.performance

import io.haerong22.ticketing.application.performance.GetPerformanceListUseCase
import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.interfaces.web.CommonResponse
import io.haerong22.ticketing.interfaces.web.performance.request.ReserveSeatRequest
import io.haerong22.ticketing.interfaces.web.performance.response.*
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/performances")
class PerformanceController(
    private val getPerformanceListUseCase: GetPerformanceListUseCase,
) {

    @GetMapping
    fun getPerformanceList(
        @RequestHeader("wq-token") token: String,
        @RequestParam("page_no") pageNo: Int,
        @RequestParam("page_size") pageSize: Int,
    ): CommonResponse<PerformanceListResponse> {
        val result = getPerformanceListUseCase(Pageable(pageNo, pageSize))

        return CommonResponse.ok(
            PerformanceListResponse(
                result.list.map { PerformanceListResponse.Performance.toResponse(it) },
                result.pageInfo
            )
        )
    }

    @GetMapping("/{performance_id}")
    fun getPerformanceScheduleList(
        @RequestHeader("wq-token") token: String,
        @PathVariable("performance_id") performanceId: Long,
    ): CommonResponse<PerformanceScheduleListResponse> {
        return CommonResponse.ok(
            PerformanceScheduleListResponse(
                "콘서트 제목!!",
                "콘서트 내용!!",
                listOf(
                    PerformanceScheduleListResponse.Schedule(
                        1,
                        LocalDateTime.of(2024, 4, 15, 17, 0, 0),
                        LocalDateTime.of(2024, 5, 5, 17, 0, 0),
                        LocalDateTime.of(2024, 5, 5, 20, 0, 0),
                    ),
                    PerformanceScheduleListResponse.Schedule(
                        1,
                        LocalDateTime.of(2024, 5, 15, 17, 0, 0),
                        LocalDateTime.of(2024, 6, 5, 17, 0, 0),
                        LocalDateTime.of(2024, 6, 5, 20, 0, 0),
                    ),
                )
            )
        )
    }

    @GetMapping("/{performance_schedule_id}/seats")
    fun getAvailableSeatList(
        @RequestHeader("wq-token") token: String,
        @PathVariable("performance_schedule_id") performanceId: Long,
    ): CommonResponse<AvailableSeatListResponse> {
        return CommonResponse.ok(
            AvailableSeatListResponse(
                listOf(
                    Seat(10, 1, 10000),
                    Seat(15, 4, 20000),
                ),
                50,
                2
            )
        )
    }

    @PostMapping("/{performance_schedule_id}/reservation")
    fun reserveSeat(
        @RequestHeader("wq-token") token: String,
        @PathVariable("performance_schedule_id") performanceId: Long,
        @RequestBody request: ReserveSeatRequest,
    ): CommonResponse<ReserveSeatResponse> {
        return CommonResponse.ok(
            ReserveSeatResponse(1)
        )
    }
}
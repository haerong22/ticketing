package io.haerong22.ticketing.interfaces.web.performance

import io.haerong22.ticketing.application.performance.GetPerformanceListUseCase
import io.haerong22.ticketing.application.performance.GetPerformanceScheduleListUseCase
import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.interfaces.web.CommonResponse
import io.haerong22.ticketing.interfaces.web.performance.request.ReserveSeatRequest
import io.haerong22.ticketing.interfaces.web.performance.response.AvailableSeatListResponse
import io.haerong22.ticketing.interfaces.web.performance.response.PerformanceListResponse
import io.haerong22.ticketing.interfaces.web.performance.response.PerformanceScheduleListResponse
import io.haerong22.ticketing.interfaces.web.performance.response.ReserveSeatResponse
import io.haerong22.ticketing.interfaces.web.performance.response.Seat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/performances")
class PerformanceController(
    private val getPerformanceListUseCase: GetPerformanceListUseCase,
    private val getPerformanceScheduleListUseCase: GetPerformanceScheduleListUseCase,
    private val mapper: PerformanceResponseMapper,
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
        val result = getPerformanceScheduleListUseCase(performanceId)
        return CommonResponse.ok(mapper.toResponse(result))
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
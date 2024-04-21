package io.haerong22.ticketing.interfaces.controller.performance

import io.haerong22.ticketing.application.performance.GetAvailableSeatListUseCase
import io.haerong22.ticketing.application.performance.GetPerformanceListUseCase
import io.haerong22.ticketing.application.performance.GetPerformanceScheduleListUseCase
import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.interfaces.controller.CommonResponse
import io.haerong22.ticketing.interfaces.controller.common.QueueToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/performances")
class PerformanceController(
    private val getPerformanceListUseCase: GetPerformanceListUseCase,
    private val getPerformanceScheduleListUseCase: GetPerformanceScheduleListUseCase,
    private val getAvailableSeatListUseCase: GetAvailableSeatListUseCase,
) {

    @GetMapping
    fun getPerformanceList(
        @QueueToken token: String,
        @RequestParam("page_no") pageNo: Int,
        @RequestParam("page_size") pageSize: Int,
    ): CommonResponse<PerformanceResponse.PerformanceList> {
        val result = getPerformanceListUseCase(Pageable(pageNo, pageSize))
        return CommonResponse.ok(
            PerformanceResponse.PerformanceList.toResponse(result)
        )
    }

    @GetMapping("/{performance_id}")
    fun getPerformanceScheduleList(
        @QueueToken token: String,
        @PathVariable("performance_id") performanceId: Long,
    ): CommonResponse<PerformanceResponse.PerformanceScheduleList> {
        val result = getPerformanceScheduleListUseCase(performanceId)
        return CommonResponse.ok(
            PerformanceResponse.PerformanceScheduleList.toResponse(result)
        )
    }

    @GetMapping("/{performance_schedule_id}/seats")
    fun getAvailableSeatList(
        @QueueToken token: String,
        @PathVariable("performance_schedule_id") performanceScheduleId: Long,
    ): CommonResponse<PerformanceResponse.AvailableSeatList> {
        val result = getAvailableSeatListUseCase(performanceScheduleId)
        return CommonResponse.ok(
            PerformanceResponse.AvailableSeatList.toResponse(result)
        )
    }
}

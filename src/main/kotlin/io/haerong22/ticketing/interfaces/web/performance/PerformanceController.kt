package io.haerong22.ticketing.interfaces.web.performance

import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.interfaces.web.CommonResponse
import io.haerong22.ticketing.interfaces.web.performance.request.ReserveSeatRequest
import io.haerong22.ticketing.interfaces.web.performance.response.*
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/performances")
class PerformanceController {

    @GetMapping
    fun getPerformanceInfoList(
        @RequestHeader("wq-token") token: String,
    ): CommonResponse<PerformanceInfoListResponse> {
        return CommonResponse.ok(
            PerformanceInfoListResponse(
                listOf(
                    PerformanceInfo(1, "콘서트 제목!!", "콘서트 내용!!"),
                    PerformanceInfo(2, "콘서트 제목2!!", "콘서트 내용2!!"),
                ),
                PageInfo(1, 5, 10L)
            )
        )
    }

    @GetMapping("/{performance_info_id}")
    fun getPerformanceList(
        @RequestHeader("wq-token") token: String,
        @PathVariable("performance_info_id") performanceInfoId: Long,
    ): CommonResponse<PerformanceListResponse> {
        return CommonResponse.ok(
            PerformanceListResponse(
                "콘서트 제목!!",
                "콘서트 내용!!",
                listOf(
                    PerformanceDate(
                        1,
                        LocalDateTime.of(2024, 4, 15, 17, 0, 0),
                        LocalDateTime.of(2024, 5, 5, 17, 0, 0),
                        LocalDateTime.of(2024, 5, 5, 20, 0, 0),
                    ),
                    PerformanceDate(
                        1,
                        LocalDateTime.of(2024, 5, 15, 17, 0, 0),
                        LocalDateTime.of(2024, 6, 5, 17, 0, 0),
                        LocalDateTime.of(2024, 6, 5, 20, 0, 0),
                    ),
                )
            )
        )
    }

    @GetMapping("/{performance_id}/seats")
    fun getAvailableSeatList(
        @RequestHeader("wq-token") token: String,
        @PathVariable("performance_id") performanceId: Long,
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

    @PostMapping("/{performance_id}/reservation")
    fun reserveSeat(
        @RequestHeader("wq-token") token: String,
        @PathVariable("performance_id") performanceId: Long,
        @RequestBody request: ReserveSeatRequest,
    ): CommonResponse<ReserveSeatResponse> {
        return CommonResponse.ok(
            ReserveSeatResponse(1)
        )
    }
}
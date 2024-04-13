package io.haerong22.ticketing.interfaces.web.performance

import io.haerong22.ticketing.application.performance.GetAvailableSeatListUseCase
import io.haerong22.ticketing.application.performance.GetPerformanceListUseCase
import io.haerong22.ticketing.application.performance.GetPerformanceScheduleListUseCase
import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.Performance
import io.haerong22.ticketing.domain.performance.PerformanceSchedule
import io.haerong22.ticketing.domain.performance.Seat
import io.haerong22.ticketing.interfaces.web.WebTestSupport
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(PerformanceController::class)
class PerformanceControllerTest : WebTestSupport() {

    @MockBean
    lateinit var getPerformanceListUseCase: GetPerformanceListUseCase

    @MockBean
    lateinit var getPerformanceScheduleListUseCase: GetPerformanceScheduleListUseCase

    @MockBean
    lateinit var getAvailableSeatListUseCase: GetAvailableSeatListUseCase

    @Test
    fun `공연 리스트 조회 성공`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val pageNo = 1
        val pageSize = 2
        val pageInfo = PageInfo(pageNo, pageSize, 2)

        given(getPerformanceListUseCase(any()))
            .willReturn(WithPage(listOf(), pageInfo))

        // then
        mockMvc.perform(
            get("/api/performances")
                .param("page_no", pageNo.toString())
                .param("page_size", pageSize.toString())
                .header("wq-token", token)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.body.performances").isArray)
    }

    @Test
    fun `공연 스케줄 리스트 조회 성공`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val performanceId = 1L
        val performance = Performance(
            performanceId,
            "공연1",
            "내용1",
            listOf(
                PerformanceSchedule(
                    performanceScheduleId = 1L,
                    startAt = LocalDateTime.of(2024, 4, 12, 17, 0, 0),
                    endAt = LocalDateTime.of(2024, 4, 12, 20, 0, 0),
                    reservationAt = LocalDateTime.of(2024, 4, 1, 17, 0, 0),
                )
            )
        )

        given(getPerformanceScheduleListUseCase(performanceId))
            .willReturn(performance)

        // then
        mockMvc.perform(
            get("/api/performances/%d".format(performanceId))
                .header("wq-token", token)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.body.title").value("공연1"))
            .andExpect(jsonPath("$.body.content").value("내용1"))
            .andExpect(jsonPath("$.body.date").isArray)
            .andExpect(jsonPath("$.body.date[0].performance_schedule_id").value("1"))
            .andExpect(jsonPath("$.body.date[0].reservation_at").value("2024-04-01T17:00:00"))
            .andExpect(jsonPath("$.body.date[0].start_at").value("2024-04-12T17:00:00"))
            .andExpect(jsonPath("$.body.date[0].end_at").value("2024-04-12T20:00:00"))
    }

    @Test
    fun `예약 가능한 좌석 리스트 조회 성공`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val performanceScheduleId = 1L
        val expected = listOf(
            Seat(
                seatId = 1L,
                performanceScheduleId = 1L,
                seatNo = 1,
                price = 10000,
                SeatStatus.OPEN
            ),
        )

        given(getAvailableSeatListUseCase(performanceScheduleId))
            .willReturn(expected)

        // then
        mockMvc.perform(
            get("/api/performances/%d/seats".format(performanceScheduleId))
                .header("wq-token", token)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.body.available_seat").isArray)
            .andExpect(jsonPath("$.body.available_seat[0].seat_id").value("1"))
            .andExpect(jsonPath("$.body.available_seat[0].seat_no").value("1"))
            .andExpect(jsonPath("$.body.available_seat[0].price").value("10000"))
    }
}
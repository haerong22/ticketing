package io.haerong22.ticketing.application.performance.unit

import io.haerong22.ticketing.application.performance.GetAvailableSeatListUseCase
import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.PerformanceReader
import io.haerong22.ticketing.domain.performance.Seat
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class GetAvailableSeatListUseCaseTest {

    @InjectMocks
    private lateinit var sut: GetAvailableSeatListUseCase

    @Mock
    private lateinit var performanceReader: PerformanceReader

    @Test
    fun `공연 스케줄 리스트 조회`() {
        // given
        val performanceScheduleId = 1L

        given(performanceReader.getAvailableSeatList(performanceScheduleId))
            .willReturn(listOf(Seat(1L, 1, 10000, SeatStatus.OPEN)))

        // when
        val result = sut(performanceScheduleId)

        // then
        verify(performanceReader, times(1)).getAvailableSeatList(performanceScheduleId)
        assertThat(result).hasSize(1)
        assertThat(result[0].seatId).isEqualTo(1L)
        assertThat(result[0].seatNo).isEqualTo(1)
        assertThat(result[0].price).isEqualTo(10000)
    }
}
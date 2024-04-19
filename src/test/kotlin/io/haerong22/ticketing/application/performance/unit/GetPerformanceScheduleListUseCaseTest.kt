package io.haerong22.ticketing.application.performance.unit

import io.haerong22.ticketing.application.performance.GetPerformanceScheduleListUseCase
import io.haerong22.ticketing.domain.performance.Performance
import io.haerong22.ticketing.domain.performance.PerformanceService
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
class GetPerformanceScheduleListUseCaseTest {

    @InjectMocks
    private lateinit var sut: GetPerformanceScheduleListUseCase

    @Mock
    private lateinit var performanceService: PerformanceService

    @Test
    fun `공연 스케줄 리스트 조회`() {
        // given
        val performanceId = 1L
        val expected = Performance(performanceId, "공연", "제목", listOf())

        given(performanceService.getPerformance(performanceId)).willReturn(expected)

        // when
        val result = sut(performanceId)

        // then
        verify(performanceService, times(1)).getPerformance(performanceId)
        assertThat(result.performanceId).isEqualTo(1L)
        assertThat(result.title).isEqualTo("공연")
        assertThat(result.content).isEqualTo("제목")
        assertThat(result.schedules).isEmpty()
    }
}
package io.haerong22.ticketing.domain.performance

import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
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
class PerformanceServiceTest {

    @InjectMocks
    lateinit var sut: PerformanceService

    @Mock
    lateinit var performanceReaderRepository: PerformanceReaderRepository

    @Test
    fun `공연 정보 리스트 조회`() {
        // given
        val pageable = Pageable(1, 5)
        val pageInfo = PageInfo(1, 5, 0)
        val expected = WithPage<Performance>(listOf(), pageInfo)

        given(performanceReaderRepository.getPerformanceList(pageable))
            .willReturn(expected)

        // when
        val result = sut.getPerformanceList(pageable)

        // then
        verify(performanceReaderRepository, times(1)).getPerformanceList(pageable)
        assertThat(result.list).asList()
        assertThat(result.pageInfo).isEqualTo(pageInfo)
    }

    @Test
    fun `공연 스케줄 리스트 조회`() {
        // given
        val performanceId = 1L

        given(performanceReaderRepository.getPerformance(performanceId))
            .willReturn(Performance(performanceId, "공연", "내용", listOf()))

        // when
        val result = sut.getPerformanceScheduleList(performanceId)

        // then
        verify(performanceReaderRepository, times(1)).getPerformance(performanceId)
        assertThat(result.performanceId).isEqualTo(1L)
        assertThat(result.title).isEqualTo("공연")
        assertThat(result.content).isEqualTo("내용")
        assertThat(result.schedules).isEmpty()
    }
}
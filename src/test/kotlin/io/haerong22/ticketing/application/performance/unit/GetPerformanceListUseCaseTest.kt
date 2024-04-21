package io.haerong22.ticketing.application.performance.unit

import io.haerong22.ticketing.application.performance.GetPerformanceListUseCase
import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
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
class GetPerformanceListUseCaseTest {

    @InjectMocks
    private lateinit var sut: GetPerformanceListUseCase

    @Mock
    private lateinit var performanceService: PerformanceService

    @Test
    fun `공연 정보 리스트 조회`() {
        // given
        val pageable = Pageable(1, 5)
        val pageInfo = PageInfo(1, 5, 2)
        val list = listOf(
            Performance(1L, "공연1", "내용1"),
            Performance(2L, "공연2", "내용2"),
        )
        val expected = WithPage(list, pageInfo)

        given(performanceService.getPerformanceList(pageable)).willReturn(expected)

        // when
        val result = sut(pageable)

        // then
        verify(performanceService, times(1)).getPerformanceList(pageable)
        assertThat(result.list).hasSize(2)
        assertThat(result.pageInfo).isEqualTo(pageInfo)
    }
}

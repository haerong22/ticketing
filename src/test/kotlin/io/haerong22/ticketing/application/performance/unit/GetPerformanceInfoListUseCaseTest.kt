package io.haerong22.ticketing.application.performance.unit

import io.haerong22.ticketing.application.performance.GetPerformanceInfoListUseCase
import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.performance.PerformanceInfo
import io.haerong22.ticketing.domain.performance.PerformanceReader
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple.tuple
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class GetPerformanceInfoListUseCaseTest {

    @InjectMocks
    private lateinit var sut: GetPerformanceInfoListUseCase

    @Mock
    private lateinit var performanceReader: PerformanceReader

    @Test
    fun `공연 정보 리스트 조회`() {
        // given
        val pageable = Pageable(1, 5)
        val pageInfo = PageInfo(1, 5, 2)
        val list = listOf(
            PerformanceInfo(1L, "공연1", "내용1"),
            PerformanceInfo(2L, "공연2", "내용2"),
        )
        val expected = WithPage(list, pageInfo)

        given(performanceReader.getPerformanceInfoList(pageable)).willReturn(expected)

        // when
        val result = sut(pageable)

        // then
        verify(performanceReader, times(1)).getPerformanceInfoList(pageable)
        assertThat(result.list).hasSize(2)
        assertThat(result.pageInfo).isEqualTo(pageInfo)
    }
}
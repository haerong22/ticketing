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
        val expected = WithPage<PerformanceInfo>(listOf(), pageInfo)

        given(performanceReaderRepository.getPerformanceInfoList(pageable))
            .willReturn(expected)

        // when
        val result = sut.getPerformanceInfoList(pageable)

        // then
        verify(performanceReaderRepository, times(1)).getPerformanceInfoList(pageable)
        assertThat(result.list).asList()
        assertThat(result.pageInfo).isEqualTo(pageInfo)
    }
}
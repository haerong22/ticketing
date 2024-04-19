package io.haerong22.ticketing.domain.performance

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class PerformanceServiceTest {

    @InjectMocks
    private lateinit var sut: PerformanceService

    @Mock
    private lateinit var performanceReader: PerformanceReader

    @Mock
    private lateinit var performanceStore: PerformanceStore

    @Test
    fun `좌석을 예약상태에서 예약가능상태로 변경한다`() {
        // given
        val seatIds = listOf(1L, 2L)

        // when
        sut.openSeat(seatIds)

        // then
        verify(performanceStore, times(1)).openSeat(seatIds)
    }
}
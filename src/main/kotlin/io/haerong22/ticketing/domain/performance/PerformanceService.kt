package io.haerong22.ticketing.domain.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import org.springframework.stereotype.Component

@Component
class PerformanceService(
    private val performanceReaderRepository: PerformanceReaderRepository,
) {

    fun getPerformanceList(pageable: Pageable) : WithPage<Performance> {
        return performanceReaderRepository.getPerformanceList(pageable)
    }
}
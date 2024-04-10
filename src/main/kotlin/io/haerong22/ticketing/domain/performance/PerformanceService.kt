package io.haerong22.ticketing.domain.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import org.springframework.stereotype.Component

@Component
class PerformanceService(
    private val performanceReaderRepository: PerformanceReaderRepository,
) {

    fun getPerformanceInfoList(pageable: Pageable) : WithPage<PerformanceInfo> {
        return performanceReaderRepository.getPerformanceInfoList(pageable)
    }
}
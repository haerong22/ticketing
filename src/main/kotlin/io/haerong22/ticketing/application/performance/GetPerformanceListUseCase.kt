package io.haerong22.ticketing.application.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.performance.Performance
import io.haerong22.ticketing.domain.performance.PerformanceService
import org.springframework.stereotype.Service

@Service
class GetPerformanceListUseCase(
    private val performanceService: PerformanceService,
) {

    operator fun invoke(pageable: Pageable): WithPage<Performance> {
        return performanceService.getPerformanceList(pageable)
    }
}
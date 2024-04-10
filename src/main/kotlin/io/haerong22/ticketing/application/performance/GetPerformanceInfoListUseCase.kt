package io.haerong22.ticketing.application.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.performance.PerformanceInfo
import io.haerong22.ticketing.domain.performance.PerformanceService
import org.springframework.stereotype.Service

@Service
class GetPerformanceInfoListUseCase(
    private val performanceService: PerformanceService,
) {

    operator fun invoke(pageable: Pageable) : WithPage<PerformanceInfo> {
        return performanceService.getPerformanceInfoList(pageable)
    }
}
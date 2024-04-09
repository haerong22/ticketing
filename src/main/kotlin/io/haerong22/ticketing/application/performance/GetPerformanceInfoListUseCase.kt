package io.haerong22.ticketing.application.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.performance.PerformanceInfo
import io.haerong22.ticketing.domain.performance.PerformanceReader
import org.springframework.stereotype.Service

@Service
class GetPerformanceInfoListUseCase(
    private val performanceReader: PerformanceReader,
) {

    operator fun invoke(pageable: Pageable) : WithPage<PerformanceInfo> {
        return performanceReader.getPerformanceInfoList(pageable)
    }
}
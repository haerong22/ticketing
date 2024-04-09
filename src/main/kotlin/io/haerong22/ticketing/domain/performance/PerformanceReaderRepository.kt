package io.haerong22.ticketing.domain.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage

interface PerformanceReaderRepository {

    fun getPerformanceInfoList(pageable: Pageable) : WithPage<PerformanceInfo>
}
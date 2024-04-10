package io.haerong22.ticketing.domain.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage

interface PerformanceReaderRepository {

    fun getPerformanceList(pageable: Pageable) : WithPage<Performance>
}
package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.performance.PerformanceInfo
import io.haerong22.ticketing.domain.performance.PerformanceReaderRepository
import org.springframework.stereotype.Repository

@Repository
class PerformanceReaderRepositoryImpl() : PerformanceReaderRepository {

    override fun getPerformanceInfoList(pageable: Pageable): WithPage<PerformanceInfo> {
        TODO("Not yet implemented")
    }
}
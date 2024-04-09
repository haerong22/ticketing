package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.performance.PerformanceInfo
import io.haerong22.ticketing.domain.performance.PerformanceReaderRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class PerformanceReaderRepositoryImpl(
    private val performanceInfoJpaRepository: PerformanceInfoJpaRepository,
) : PerformanceReaderRepository {

    override fun getPerformanceInfoList(pageable: Pageable): WithPage<PerformanceInfo> {
        val pageRequest = PageRequest.of(pageable.pageNo - 1, pageable.pageSize)
        val result = performanceInfoJpaRepository.findAll(pageRequest)

        return WithPage(
            result.content.map { it.toDomain() },
            PageInfo(pageable.pageNo, pageable.pageSize, result.totalElements)
        )
    }
}
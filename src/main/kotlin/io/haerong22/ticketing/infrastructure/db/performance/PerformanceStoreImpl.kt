package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.performance.PerformanceStore
import io.haerong22.ticketing.domain.performance.Seat
import org.springframework.stereotype.Repository

@Repository
class PerformanceStoreImpl(
    private val performanceJpaRepository: PerformanceJpaRepository,
) : PerformanceStore{

    override fun save(seat: Seat): Seat {
        TODO("Not yet implemented")
    }
}
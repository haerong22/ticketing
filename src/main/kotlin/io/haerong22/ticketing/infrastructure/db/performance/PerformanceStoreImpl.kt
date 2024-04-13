package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.performance.PerformanceStore
import io.haerong22.ticketing.domain.performance.Seat
import org.springframework.stereotype.Repository

@Repository
class PerformanceStoreImpl(
    private val seatJpaRepository: SeatJpaRepository,
) : PerformanceStore {

    override fun save(seat: Seat): Seat {
        val seatEntity = SeatEntity.of(seat)
        return seatJpaRepository.save(seatEntity).toDomain()
    }
}
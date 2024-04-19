package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.Performance
import io.haerong22.ticketing.domain.performance.PerformanceReader
import io.haerong22.ticketing.domain.performance.Seat
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class PerformanceReaderImpl(
    private val performanceJpaRepository: PerformanceJpaRepository,
    private val performanceScheduleJpaRepository: PerformanceScheduleJpaRepository,
    private val seatJpaRepository: SeatJpaRepository,
) : PerformanceReader {
    override fun getPerformance(performanceId: Long): Performance? {
        return performanceJpaRepository.findById(performanceId).orElse(null)
            ?.let {
                val performanceScheduleEntity = performanceScheduleJpaRepository.findByPerformanceId(performanceId)
                return Performance(
                    performanceId = performanceId,
                    title = it.title,
                    content = it.content,
                    schedules = performanceScheduleEntity.map { schedule -> schedule.toDomain() }
                )
            }
    }

    override fun getAvailableSeatList(performanceScheduleId: Long): List<Seat> {
        return seatJpaRepository.findByPerformanceScheduleIdAndStatus(performanceScheduleId, SeatStatus.OPEN)
            .map { it.toDomain() }
    }

    override fun getSeatWithLock(seatId: Long): Seat? {
        return seatJpaRepository.findByIdForUpdate(seatId)
            ?.toDomain()
    }

    override fun getPerformanceList(pageable: Pageable): WithPage<Performance> {
        val pageRequest = PageRequest.of(pageable.pageNo - 1, pageable.pageSize)
        val result = performanceJpaRepository.findAll(pageRequest)

        return WithPage(
            result.content.map { it.toDomain() },
            PageInfo(pageable.pageNo, pageable.pageSize, result.totalElements)
        )
    }
}
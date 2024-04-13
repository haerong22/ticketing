package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.Performance
import io.haerong22.ticketing.domain.performance.PerformanceException
import io.haerong22.ticketing.domain.performance.PerformanceReader
import io.haerong22.ticketing.domain.performance.PerformanceResponseCode.PERFORMANCE_NOT_FOUND
import io.haerong22.ticketing.domain.performance.Seat
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class PerformanceReaderImpl(
    private val performanceJpaRepository: PerformanceJpaRepository,
    private val performanceScheduleJpaRepository: PerformanceScheduleJpaRepository,
    private val seatJpaRepository: SeatJpaRepository,
) : PerformanceReader {
    override fun getPerformance(performanceId: Long): Performance {
        val performanceEntity = performanceJpaRepository.findById(performanceId)
            .orElseThrow { throw PerformanceException(PERFORMANCE_NOT_FOUND) }
        val performanceScheduleEntity = performanceScheduleJpaRepository.findByPerformanceId(performanceId)

        return Performance(
            performanceId = performanceId,
            title = performanceEntity.title,
            content = performanceEntity.content,
            schedules = performanceScheduleEntity.map { it.toDomain() }
        )
    }

    override fun getAvailableSeatList(performanceScheduleId: Long): List<Seat> {
        return seatJpaRepository.findByPerformanceScheduleIdAndStatus(performanceScheduleId, SeatStatus.OPEN)
            .map { it.toDomain() }
    }

    override fun getSeatWithLock(seatId: Long): Seat {
        TODO("Not yet implemented")
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
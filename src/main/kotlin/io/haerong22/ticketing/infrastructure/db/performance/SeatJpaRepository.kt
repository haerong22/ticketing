package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.enums.SeatStatus
import org.springframework.data.jpa.repository.JpaRepository

interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {

    fun findByPerformanceScheduleIdAndStatus(scheduleId: Long, status: SeatStatus) : List<SeatEntity>
}
package io.haerong22.ticketing.infrastructure.db.performance

import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceScheduleJpaRepository : JpaRepository<PerformanceScheduleEntity, Long> {

    fun findByPerformanceId(performanceId: Long) : List<PerformanceScheduleEntity>
}
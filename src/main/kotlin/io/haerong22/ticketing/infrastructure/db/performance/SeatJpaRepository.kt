package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.enums.SeatStatus
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {

    fun findByPerformanceScheduleIdAndStatus(scheduleId: Long, status: SeatStatus): List<SeatEntity>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SeatEntity s where s.id=:seatId")
    fun findByIdForUpdate(seatId: Long): SeatEntity?

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update SeatEntity s set s.status=:status where s.id in :seatIds")
    fun updateStatus(seatIds: List<Long>, status: SeatStatus)
}

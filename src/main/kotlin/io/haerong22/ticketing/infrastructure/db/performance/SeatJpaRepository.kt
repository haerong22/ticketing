package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.enums.SeatStatus
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.*

interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {

    fun findByPerformanceScheduleIdAndStatus(scheduleId: Long, status: SeatStatus) : List<SeatEntity>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SeatEntity s where s.id=:seatId")
    fun findByIdForUpdate(seatId: Long): Optional<SeatEntity>
}
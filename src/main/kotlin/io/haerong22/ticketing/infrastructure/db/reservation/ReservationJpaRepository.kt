package io.haerong22.ticketing.infrastructure.db.reservation

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from ReservationEntity r where r.id=:reservationId")
    fun findByIdForUpdate(reservationId: Long): Optional<ReservationEntity>
}
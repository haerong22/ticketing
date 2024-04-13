package io.haerong22.ticketing.infrastructure.db.reservation

import org.springframework.data.jpa.repository.JpaRepository

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {
}
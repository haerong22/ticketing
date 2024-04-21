package io.haerong22.ticketing.infrastructure.db.user

import org.springframework.data.jpa.repository.JpaRepository

interface PointHistoryJpaRepository : JpaRepository<PointHistoryEntity, Long>

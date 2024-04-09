package io.haerong22.ticketing.infrastructure.db.performance

import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceInfoJpaRepository : JpaRepository<PerformanceInfoEntity, Long> {
}
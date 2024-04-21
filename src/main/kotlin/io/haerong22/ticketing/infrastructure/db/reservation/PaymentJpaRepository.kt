package io.haerong22.ticketing.infrastructure.db.reservation

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<PaymentEntity, Long>

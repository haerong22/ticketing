package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface QueueJpaRepository : JpaRepository<QueueEntity, Long> {

    @Query("select count(q.id) from QueueEntity q where q.id < :queueId and q.status = 'WAITING'")
    fun rank(queueId: Long): Int

    fun findByToken(token: String): Optional<QueueEntity>

    fun deleteByToken(token: String)

    fun countByStatus(status: QueueStatus): Int

    @Query("select q.id from QueueEntity q where q.status=:status")
    fun findIdByStatusOrderById(status: QueueStatus, pageable: Pageable): List<Long>

    fun deleteByExpiredAtBefore(date: LocalDateTime): Int

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
        "update QueueEntity q " +
                "set q.status=:status, q.expiredAt=:expiredAt " +
                "where q.id in :ids"
    )
    fun updateStatusByIds(ids: List<Long>, status: QueueStatus, expiredAt: LocalDateTime): Int
}
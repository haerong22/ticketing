package io.haerong22.ticketing.infrastructure.db.queue

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface QueueJpaRepository : JpaRepository<QueueEntity, Long> {

    @Query("select count(q.id) from QueueEntity q where q.id < :queueId and q.status = 'WAITING'")
    fun rank(queueId: Long): Int

    fun findByToken(token: String) : Optional<QueueEntity>

    fun deleteByToken(token: String)
}
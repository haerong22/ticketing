package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.WaitingQueue
import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "queue")
class QueueEntity(

    @Column(unique = true)
    val token: String,

    @Enumerated(EnumType.STRING)
    val status: QueueStatus,
    val expiredAt: LocalDateTime? = null,
) : BaseEntity() {

    companion object {

        fun of(waitingQueue: WaitingQueue): QueueEntity {
            return QueueEntity(
                token = waitingQueue.token,
                status = waitingQueue.status,
                expiredAt = waitingQueue.expiredAt,
            ).apply { id = waitingQueue.queueId }
        }
    }

    fun toDomain(rank: Int) : WaitingQueue {
        return WaitingQueue(
            queueId = id!!,
            token = token,
            rank = rank,
            status = status,
            expiredAt = expiredAt,
        )
    }
}
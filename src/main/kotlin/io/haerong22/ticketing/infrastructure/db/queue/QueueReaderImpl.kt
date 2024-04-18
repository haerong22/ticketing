package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.queue.QueueException
import io.haerong22.ticketing.domain.queue.QueueReader
import io.haerong22.ticketing.domain.queue.QueueResponseCode
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.springframework.stereotype.Repository

@Repository
class QueueReaderImpl(
    private val queueJpaRepository: QueueJpaRepository,
) : QueueReader {

    override fun getQueueStatus(token: String): WaitingQueue {
        return queueJpaRepository.findByToken(token)
            .orElseThrow { throw QueueException(QueueResponseCode.QUEUE_TOKEN_NOT_FOUND) }
            .let {
                val rank = queueJpaRepository.rank(it.id!!)
                it.toDomain(rank)
            }
    }

    override fun getActiveCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getTargetTokenIds(targetCount: Int): List<Long> {
        TODO("Not yet implemented")
    }
}
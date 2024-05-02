package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueReader
import io.haerong22.ticketing.domain.queue.WaitingQueue

// @Repository
class QueueReaderImpl(
    private val queueJpaRepository: QueueJpaRepository,
) : QueueReader {

    override fun getQueueStatus(token: String): WaitingQueue? {
        return queueJpaRepository.findByToken(token)
            ?.let {
                val rank = queueJpaRepository.rank(it.id!!)
                it.toDomain(rank)
            }
    }

    override fun getActiveCount(): Int {
        return queueJpaRepository.countByStatus(QueueStatus.PROCEEDING)
    }
}

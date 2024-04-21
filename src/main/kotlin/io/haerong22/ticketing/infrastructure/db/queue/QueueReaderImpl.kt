package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueReader
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
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

    override fun getTargetTokenIds(targetCount: Int): List<Long> {
        val pageable = PageRequest.of(0, targetCount)
        return queueJpaRepository.findIdByStatusOrderById(QueueStatus.WAITING, pageable)
    }
}

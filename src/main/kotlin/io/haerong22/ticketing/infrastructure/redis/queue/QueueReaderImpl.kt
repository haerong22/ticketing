package io.haerong22.ticketing.infrastructure.redis.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueReader
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.springframework.stereotype.Repository

@Repository
class QueueReaderImpl(
    private val queueRedisRepository: QueueRedisRepository,
) : QueueReader {

    override fun getQueueStatus(token: String): WaitingQueue? {
        return queueRedisRepository.getRank(token)
            ?.let {
                WaitingQueue(
                    token = token,
                    rank = it.toInt(),
                    status = QueueStatus.WAITING,
                )
            }
            ?: if (queueRedisRepository.isProceedToken(token)) {
                return WaitingQueue(
                    token = token,
                    rank = 0,
                    status = QueueStatus.PROCEEDING,
                )
            } else {
                return null
            }
    }

    override fun getActiveCount(): Int {
        return queueRedisRepository.getActiveCount()
    }
}

package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueStore
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

// @Repository
class QueueStoreImpl(
    private val queueJpaRepository: QueueJpaRepository,
) : QueueStore {

    override fun enter(waitingQueue: WaitingQueue): WaitingQueue {
        val queueEntity = QueueEntity.of(waitingQueue)
        return queueJpaRepository.save(queueEntity)
            .let {
                val rank = queueJpaRepository.rank(it.id!!)
                it.toDomain(rank)
            }
    }

    override fun exit(token: String) {
        queueJpaRepository.deleteByToken(token)
    }

    @Transactional
    override fun clearExpiredToken() {
        queueJpaRepository.deleteByExpiredAtBefore(LocalDateTime.now())
    }

    @Transactional
    override fun activeTokens(targetCount: Int) {
        val pageable = PageRequest.of(0, targetCount)
        val targets = queueJpaRepository.findIdByStatusOrderById(QueueStatus.WAITING, pageable)

        val expiredAt = LocalDateTime.now().plusMinutes(5)
        queueJpaRepository.updateStatusByIds(targets, QueueStatus.PROCEEDING, expiredAt)
    }
}

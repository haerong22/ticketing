package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.queue.QueueStore
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.springframework.stereotype.Repository

@Repository
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

    override fun clearExpiredToken() {
        TODO("Not yet implemented")
    }

    override fun activeTokens(targets: List<Long>) {
        TODO("Not yet implemented")
    }
}
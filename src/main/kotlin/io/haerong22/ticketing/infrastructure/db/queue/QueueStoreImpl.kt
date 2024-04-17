package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.queue.QueueStore
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.springframework.stereotype.Repository

@Repository
class QueueStoreImpl : QueueStore {

    override fun enter(waitingQueue: WaitingQueue): WaitingQueue {
        TODO("Not yet implemented")
    }
}
package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.queue.QueueReader
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.springframework.stereotype.Repository

@Repository
class QueueReaderImpl : QueueReader {

    override fun getQueueStatus(token: String): WaitingQueue {
        TODO("Not yet implemented")
    }
}
package io.haerong22.ticketing.interfaces.web.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.WaitingQueue

class QueueResponse {

    data class Info(
        val token: String,
        val rank: Int,
        val status: QueueStatus,
    ) {

        companion object {
            fun toResponse(waitingQueue: WaitingQueue): Info {
                return Info(
                    token = waitingQueue.token,
                    rank = waitingQueue.rank,
                    status = waitingQueue.status,
                )
            }
        }
    }
}
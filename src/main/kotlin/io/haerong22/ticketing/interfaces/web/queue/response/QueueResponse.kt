package io.haerong22.ticketing.interfaces.web.queue.response

import io.haerong22.ticketing.domain.common.enums.QueueStatus

class QueueResponse(
    val token: String,
    val rank: Int,
    val status: QueueStatus,
) {
}
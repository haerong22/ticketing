package io.haerong22.ticketing.domain.queue

import io.haerong22.ticketing.domain.common.enums.TokenStatus
import java.time.LocalDateTime

data class WaitingQueue(
    val queueId: Long = 0,
    val token: String,
    val rank: Int,
    val status: TokenStatus,
    val expiredAt: LocalDateTime? = null,
) {

    companion object {
        fun enter(token: String) : WaitingQueue {
            return WaitingQueue(
                token = token,
                rank = 0,
                status = TokenStatus.WAITING,
            )
        }
    }
}
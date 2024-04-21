package io.haerong22.ticketing.domain.queue

import io.haerong22.ticketing.domain.common.ResponseCode

enum class QueueResponseCode(
    override val code: Int,
    override val msg: String,
) : ResponseCode {

    QUEUE_TOKEN_NOT_FOUND(4404, "토큰이 없습니다."),

    ;
}

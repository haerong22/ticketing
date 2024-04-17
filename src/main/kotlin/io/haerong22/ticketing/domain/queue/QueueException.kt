package io.haerong22.ticketing.domain.queue

import io.haerong22.ticketing.domain.common.CustomException
import io.haerong22.ticketing.domain.common.ResponseCode

class QueueException : RuntimeException, CustomException {

    override var responseCode: ResponseCode
    override var msg: String

    constructor(queueResponseCode: QueueResponseCode) : super(queueResponseCode.msg) {
        this.responseCode = queueResponseCode
        this.msg = queueResponseCode.msg
    }

    constructor(queueResponseCode: QueueResponseCode, message: String) : super(message) {
        this.responseCode = queueResponseCode
        this.msg = message
    }
}
package io.haerong22.ticketing.domain.performance

import io.haerong22.ticketing.domain.common.CustomException
import io.haerong22.ticketing.domain.common.ResponseCode

class PerformanceException : RuntimeException, CustomException {

    override var responseCode: ResponseCode
    override var msg: String

    constructor(performanceResponseCode: PerformanceResponseCode) : super(performanceResponseCode.msg) {
        this.responseCode = performanceResponseCode
        this.msg = performanceResponseCode.msg
    }

    constructor(performanceResponseCode: PerformanceResponseCode, message: String) : super(message) {
        this.responseCode = performanceResponseCode
        this.msg = message
    }
}

package io.haerong22.ticketing.domain.common

class BadRequestException : RuntimeException, CustomException {

    override var errorCode: ErrorCode
    override var msg: String

    constructor(code: ErrorCode) : super(code.msg) {
        this.errorCode = code
        this.msg = code.msg
    }

    constructor(code: ErrorCode, message: String) : super(message) {
        this.errorCode = code
        this.msg = message
    }
}
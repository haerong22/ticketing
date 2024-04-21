package io.haerong22.ticketing.domain.user

import io.haerong22.ticketing.domain.common.CustomException
import io.haerong22.ticketing.domain.common.ResponseCode

class UserException : RuntimeException, CustomException {

    override var responseCode: ResponseCode
    override var msg: String

    constructor(userResponseCode: UserResponseCode) : super(userResponseCode.msg) {
        this.responseCode = userResponseCode
        this.msg = userResponseCode.msg
    }

    constructor(userResponseCode: UserResponseCode, message: String) : super(message) {
        this.responseCode = userResponseCode
        this.msg = message
    }
}

package io.haerong22.ticketing.domain.common

interface CustomException {
    val responseCode: ResponseCode
    val msg: String
}

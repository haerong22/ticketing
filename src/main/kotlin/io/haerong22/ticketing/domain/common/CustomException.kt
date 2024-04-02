package io.haerong22.ticketing.domain.common

interface CustomException {
    val errorCode: ErrorCode
    val msg: String
}
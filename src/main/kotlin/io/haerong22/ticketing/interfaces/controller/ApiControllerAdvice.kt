package io.haerong22.ticketing.interfaces.controller

import io.haerong22.ticketing.domain.common.BadRequestException
import io.haerong22.ticketing.domain.common.CommonException
import io.haerong22.ticketing.domain.common.CustomException
import io.haerong22.ticketing.domain.performance.PerformanceException
import io.haerong22.ticketing.domain.queue.QueueException
import io.haerong22.ticketing.domain.reservation.ReservationException
import io.haerong22.ticketing.domain.user.UserException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {
    val log: Logger get() = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(
        CommonException::class,
        UserException::class,
        PerformanceException::class,
        ReservationException::class,
        QueueException::class,
    )
    fun handleBadRequestException(e: CustomException): CommonResponse<Any> {
        log.warn("CustomException : {}", e.msg)
        return CommonResponse.error(e.responseCode, e.msg)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: BadRequestException): CommonResponse<Any> {
        log.warn("BadRequestException : {}", e.msg)
        return CommonResponse.error(e.responseCode, e.msg)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): String {
        log.error("Exception : {}", e.message, e)
        return e.localizedMessage
    }
}

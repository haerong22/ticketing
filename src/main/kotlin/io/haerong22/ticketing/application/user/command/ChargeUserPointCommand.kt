package io.haerong22.ticketing.application.user.command

import io.haerong22.ticketing.domain.common.BadRequestException
import io.haerong22.ticketing.domain.common.CommonResponseCode.BAD_REQUEST

data class ChargeUserPointCommand(
    val userId: Long,
    val amount: Int,
) {

    init {
        if (userId <= 0) throw BadRequestException(BAD_REQUEST, "userId는 양수 값 입니다.")
        if (amount <= 0) throw BadRequestException(BAD_REQUEST, "amount는 양수 값 입니다.")
    }
}
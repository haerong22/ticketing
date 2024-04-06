package io.haerong22.ticketing.application.user.command

import io.haerong22.ticketing.domain.common.BadRequestException
import io.haerong22.ticketing.domain.common.CommonResponseCode.BAD_REQUEST

data class GetUserPointCommand(
    val userId: Long,
) {

    init {
        if (userId <= 0) throw BadRequestException(BAD_REQUEST, "userId는 양수 값 입니다.")
    }
}
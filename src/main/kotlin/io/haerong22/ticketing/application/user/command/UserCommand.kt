package io.haerong22.ticketing.application.user.command

import io.haerong22.ticketing.domain.common.BadRequestException
import io.haerong22.ticketing.domain.common.CommonResponseCode

class UserCommand {

    data class ChargePoint(
        val userId: Long,
        val amount: Int,
    ) {

        init {
            if (userId <= 0) throw BadRequestException(CommonResponseCode.BAD_REQUEST, "userId는 양수 값 입니다.")
            if (amount <= 0) throw BadRequestException(CommonResponseCode.BAD_REQUEST, "amount는 양수 값 입니다.")
        }
    }

    data class GetPoint(
        val userId: Long,
    ) {

        init {
            if (userId <= 0) throw BadRequestException(CommonResponseCode.BAD_REQUEST, "userId는 양수 값 입니다.")
        }
    }
}

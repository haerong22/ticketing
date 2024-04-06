package io.haerong22.ticketing.domain.user

import io.haerong22.ticketing.domain.common.BadRequestException
import io.haerong22.ticketing.domain.common.CommonResponseCode.BAD_REQUEST

data class UserPoint(
    val amount: Int,
) {
    init {
        if (amount < 0) throw BadRequestException(BAD_REQUEST, "포인트는 음수로 초기화 할 수 없습니다.")
    }
}
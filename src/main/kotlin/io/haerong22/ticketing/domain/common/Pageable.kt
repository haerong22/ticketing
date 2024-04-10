package io.haerong22.ticketing.domain.common

import io.haerong22.ticketing.domain.common.CommonResponseCode.*

class Pageable(
    val pageNo: Int,
    val pageSize: Int,
) {

    init {
        if (pageNo <= 0) throw BadRequestException(BAD_REQUEST, "pageNo는 1이상 값으로 초기화 할 수 있습니다.")
        if (pageSize <= 0) throw BadRequestException(BAD_REQUEST, "pageSize는 1이상 값으로 초기화 할 수 있습니다.")
    }
}
package io.haerong22.ticketing.domain.performance

import io.haerong22.ticketing.domain.common.ResponseCode

enum class PerformanceResponseCode(
    override val code: Int,
    override val msg: String,
) : ResponseCode {

    PERFORMANCE_NOT_FOUND(2404, "공연을 찾을 수 없습니다.")

    ;
}
package io.haerong22.ticketing.api.performance.controller.response

import io.haerong22.ticketing.domain.common.PageInfo

class PerformanceInfoListResponse(
    val performances: List<PerformanceInfo>,
    val page: PageInfo,
) {
}

class PerformanceInfo(
    val performanceInfoId: Long,
    val title: String,
    val content: String,
)
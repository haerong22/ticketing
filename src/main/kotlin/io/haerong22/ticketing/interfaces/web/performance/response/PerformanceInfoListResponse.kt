package io.haerong22.ticketing.interfaces.web.performance.response

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
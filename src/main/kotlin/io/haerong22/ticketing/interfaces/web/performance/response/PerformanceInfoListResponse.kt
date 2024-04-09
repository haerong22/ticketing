package io.haerong22.ticketing.interfaces.web.performance.response

import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.domain.performance.PerformanceInfo

class PerformanceInfoListResponse(
    val performances: List<Element>,
    val page: PageInfo,
) {
}

class Element(
    val performanceInfoId: Long,
    val title: String,
    val content: String,
) {

    companion object {

        fun toResponse(performanceInfo: PerformanceInfo) : io.haerong22.ticketing.interfaces.web.performance.response.Element {
            return Element(
                performanceInfoId = performanceInfo.performanceId,
                title = performanceInfo.title,
                content = performanceInfo.content,
            )
        }
    }
}
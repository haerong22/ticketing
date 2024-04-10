package io.haerong22.ticketing.interfaces.web.performance.response

import io.haerong22.ticketing.domain.common.PageInfo

class PerformanceListResponse(
    val performances: List<Performance>,
    val page: PageInfo,
) {

    class Performance(
        val performanceId: Long,
        val title: String,
        val content: String,
    ) {

        companion object {

            fun toResponse(performance: io.haerong22.ticketing.domain.performance.Performance) : Performance {
                return Performance(
                    performanceId = performance.performanceId,
                    title = performance.title,
                    content = performance.content,
                )
            }
        }
    }
}


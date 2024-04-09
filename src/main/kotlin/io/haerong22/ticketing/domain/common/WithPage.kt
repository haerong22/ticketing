package io.haerong22.ticketing.domain.common

class WithPage<T>(
    val list: List<T>,
    val pageInfo: PageInfo,
) {
}
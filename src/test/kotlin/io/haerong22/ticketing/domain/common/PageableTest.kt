package io.haerong22.ticketing.domain.common

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PageableTest {

    @ParameterizedTest
    @ValueSource(ints = [-1, 0])
    fun `pageNo 음수 또는 0 으로 초기화 하면 BadRequestException 이 발생한다`(pageNo: Int) {
        // given
        val pageSize = 5

        // when, then
        assertThatThrownBy { Pageable(pageNo, pageSize) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("pageNo는 1이상 값으로 초기화 할 수 있습니다.")
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0])
    fun `pageSize 음수 또는 0 으로 초기화 하면 BadRequestException 이 발생한다`(pageSize: Int) {
        // given
        val pageNo = 1

        // when, then
        assertThatThrownBy { Pageable(pageNo, pageSize) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("pageSize는 1이상 값으로 초기화 할 수 있습니다.")
    }
}
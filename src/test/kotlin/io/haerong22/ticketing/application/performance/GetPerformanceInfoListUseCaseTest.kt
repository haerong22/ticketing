package io.haerong22.ticketing.application.performance

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.infrastructure.db.performance.PerformanceInfoEntity
import io.haerong22.ticketing.infrastructure.db.performance.PerformanceInfoJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.assertj.core.groups.Tuple.*
import org.junit.jupiter.api.Test

class GetPerformanceInfoListUseCaseTest(
    private val sut: GetPerformanceInfoListUseCase,
    private val performanceInfoJpaRepository: PerformanceInfoJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `공연 정보 리스트를 조회한다`() {
        // given
        performanceInfoJpaRepository.save(PerformanceInfoEntity("공연1", "내용1"))
        performanceInfoJpaRepository.save(PerformanceInfoEntity("공연2", "내용2"))
        performanceInfoJpaRepository.save(PerformanceInfoEntity("공연3", "내용3"))

        val pageable = Pageable(1, 2)

        // when
        val result = sut(pageable)

        // then
        val totalElements = performanceInfoJpaRepository.count()

        assertThat(result.list).hasSize(2)
            .extracting("performanceId", "title", "content")
            .containsExactlyInAnyOrder(
                tuple(1L, "공연1", "내용1"),
                tuple(2L, "공연2", "내용2"),
            )
        assertThat(result.pageInfo.pageNo).isEqualTo(1)
        assertThat(result.pageInfo.pageSize).isEqualTo(2)
        assertThat(result.pageInfo.totalElements).isEqualTo(totalElements)
    }

    @Test
    fun `공연 정보가 없으면 빈 리스트를 응답한다`() {
        // given
        val pageable = Pageable(1, 2)

        // when
        val result = sut(pageable)

        // then
        val totalElements = performanceInfoJpaRepository.count()

        assertThat(result.list).isEmpty()
        assertThat(result.pageInfo.pageNo).isEqualTo(1)
        assertThat(result.pageInfo.pageSize).isEqualTo(2)
        assertThat(result.pageInfo.totalElements).isEqualTo(totalElements)
    }
}
package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.infrastructure.DbTestSupport
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import

@Import(PerformanceReaderRepositoryImpl::class)
class PerformanceReaderRepositoryImplTest(
    private val performanceReaderRepositoryImpl: PerformanceReaderRepositoryImpl,
    private val performanceJpaRepository: PerformanceJpaRepository,
) : DbTestSupport() {

    @Test
    fun `공연 정보를 조회 할 수 있다`() {
        // given
        performanceJpaRepository.save(PerformanceEntity("공연1", "내용1"))
        performanceJpaRepository.save(PerformanceEntity("공연2", "내용2"))
        performanceJpaRepository.save(PerformanceEntity("공연3", "내용3"))

        val pageable = Pageable(1, 1)

        // when
        val result = performanceReaderRepositoryImpl.getPerformanceList(pageable)

        // then
        assertThat(result.list).hasSize(1)
            .extracting("title", "content")
            .containsExactlyInAnyOrder(
                tuple("공연1", "내용1")
            )

        assertThat(result.pageInfo.pageNo).isEqualTo(1)
        assertThat(result.pageInfo.pageSize).isEqualTo(1)
        assertThat(result.pageInfo.totalElements).isEqualTo(3)
    }

    @Test
    fun `공연 정보가 없으면 빈 리스트를 응답한다`() {
        // given
        performanceJpaRepository.save(PerformanceEntity("공연1", "내용1"))
        performanceJpaRepository.save(PerformanceEntity("공연2", "내용2"))
        performanceJpaRepository.save(PerformanceEntity("공연3", "내용3"))

        val pageable = Pageable(2, 5)

        // when
        val result = performanceReaderRepositoryImpl.getPerformanceList(pageable)

        // then
        assertThat(result.list).isEmpty()

        assertThat(result.pageInfo.pageNo).isEqualTo(2)
        assertThat(result.pageInfo.pageSize).isEqualTo(5)
        assertThat(result.pageInfo.totalElements).isEqualTo(3)
    }
}
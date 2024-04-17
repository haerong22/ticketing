package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.infrastructure.DbTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.*

class QueueJpaRepositoryTest(
    private val queueJpaRepository: QueueJpaRepository,
) : DbTestSupport() {

    @ParameterizedTest
    @CsvSource("1,0", "2,0", "3,1", "4,2", "5,3")
    fun `대기 순번을 조회 할 수 있다`(queueId: Long, expected: Long) {
        // given
        val queue1 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.PROCEEDING)
        val queue2 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue3 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue4 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue5 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        queueJpaRepository.saveAll(listOf(queue1, queue2, queue3, queue4, queue5))

        // when
        val rank = queueJpaRepository.rank(queueId)

        // then
        assertThat(rank).isEqualTo(expected)
    }
}
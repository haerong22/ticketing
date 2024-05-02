package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueReader
import io.haerong22.ticketing.infrastructure.DbTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.UUID

@Disabled
// @Import(QueueReaderImpl::class)
class QueueReaderImplTest(
    private val sut: QueueReader,
    private val queueJpaRepository: QueueJpaRepository,
) : DbTestSupport() {

    @Test
    fun `토큰 상태를 조회한다`() {
        // given
        queueJpaRepository.save(QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING))
        queueJpaRepository.save(QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING))

        val token = UUID.randomUUID().toString()
        queueJpaRepository.save(QueueEntity(token = token, status = QueueStatus.WAITING))

        // when
        val result = sut.getQueueStatus(token)!!

        // then
        assertThat(result.queueId).isEqualTo(3L)
        assertThat(result.rank).isEqualTo(2)
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        assertThat(result.token).isEqualTo(token)
        assertThat(result.expiredAt).isNull()
    }

    @Test
    fun `활성 상태 토큰 개수를 조회한다`() {
        // given
        val queue1 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.PROCEEDING)
        val queue2 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.PROCEEDING)
        val queue3 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue4 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue5 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        queueJpaRepository.saveAll(listOf(queue1, queue2, queue3, queue4, queue5))

        // when
        val result = sut.getActiveCount()

        // then
        assertThat(result).isEqualTo(2)
    }
}

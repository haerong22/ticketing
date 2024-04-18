package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueException
import io.haerong22.ticketing.domain.queue.QueueReader
import io.haerong22.ticketing.infrastructure.DbTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import java.util.*

@Import(QueueReaderImpl::class)
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
        val result = sut.getQueueStatus(token)

        // then
        assertThat(result.queueId).isEqualTo(3L)
        assertThat(result.rank).isEqualTo(2)
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        assertThat(result.token).isEqualTo(token)
        assertThat(result.expiredAt).isNull()
    }

    @Test
    fun `토큰 상태를 조회 시 해당 토큰이 없으면 QueueException 이 발생한다`() {
        // given
        val token = UUID.randomUUID().toString()

        // when, then
        assertThatThrownBy { sut.getQueueStatus(token) }
            .isInstanceOf(QueueException::class.java)
            .hasMessage("토큰이 없습니다.")
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

        val pageable = PageRequest.of(0, 3)

        // when
        val result = sut.getActiveCount()

        // then
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `대기 상태 토큰 리스트를 조회한다`() {
        // given
        val queue1 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.PROCEEDING)
        val queue2 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.PROCEEDING)
        val queue3 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue4 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        val queue5 = QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING)
        queueJpaRepository.saveAll(listOf(queue1, queue2, queue3, queue4, queue5))

        // when
        val result = sut.getTargetTokenIds(2)

        // then
        assertThat(result).hasSize(2)
        assertThat(result).isEqualTo(listOf(3L, 4L))
    }
}
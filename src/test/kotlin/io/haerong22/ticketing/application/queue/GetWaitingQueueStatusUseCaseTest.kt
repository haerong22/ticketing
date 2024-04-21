package io.haerong22.ticketing.application.queue

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueException
import io.haerong22.ticketing.infrastructure.db.queue.QueueEntity
import io.haerong22.ticketing.infrastructure.db.queue.QueueJpaRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class GetWaitingQueueStatusUseCaseTest(
    private val sut: GetWaitingQueueStatusUseCase,
    private val queueJpaRepository: QueueJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `토큰 상태를 조회한다`() {
        // given
        queueJpaRepository.save(QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING))
        queueJpaRepository.save(QueueEntity(token = UUID.randomUUID().toString(), status = QueueStatus.WAITING))

        val token = UUID.randomUUID().toString()
        queueJpaRepository.save(QueueEntity(token = token, status = QueueStatus.WAITING))

        // when
        val result = sut(token)

        // then
        Assertions.assertThat(result.queueId).isEqualTo(3L)
        Assertions.assertThat(result.rank).isEqualTo(2)
        Assertions.assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        Assertions.assertThat(result.token).isEqualTo(token)
        Assertions.assertThat(result.expiredAt).isNull()
    }

    @Test
    fun `토큰 상태를 조회 시 해당 토큰이 없으면 QueueException 이 발생한다`() {
        // given
        val token = UUID.randomUUID().toString()

        // when, then
        Assertions.assertThatThrownBy { sut(token) }
            .isInstanceOf(QueueException::class.java)
            .hasMessage("토큰이 없습니다.")
    }
}

package io.haerong22.ticketing.application.queue

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.domain.common.enums.TokenStatus
import io.haerong22.ticketing.infrastructure.db.queue.QueueEntity
import io.haerong22.ticketing.infrastructure.db.queue.QueueJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class EnterWaitingQueueUseCaseTest(
    private val sut: EnterWaitingQueueUseCase,
    private val queueJpaRepository: QueueJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `대기열에 입장한다`() {
        // given
        val queue = QueueEntity(token = UUID.randomUUID().toString(), status = TokenStatus.WAITING)
        queueJpaRepository.save(queue)

        // when
        val result = sut()

        // then
        val count = queueJpaRepository.count()
        assertThat(count).isEqualTo(2)

        assertThat(result.queueId).isEqualTo(2L)
        assertThat(result.rank).isEqualTo(1)
        assertThat(result.token).isNotNull()
        assertThat(result.status).isEqualTo(TokenStatus.WAITING)
        assertThat(result.expiredAt).isNull()
    }
}
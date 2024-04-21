package io.haerong22.ticketing.application.queue

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.infrastructure.db.queue.QueueEntity
import io.haerong22.ticketing.infrastructure.db.queue.QueueJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class ExitWaitingQueueUseCaseTest(
    private val sut: ExitWaitingQueueUseCase,
    private val queueJpaRepository: QueueJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `대기열 퇴장`() {
        // given
        val token = UUID.randomUUID().toString()
        queueJpaRepository.save(QueueEntity(token = token, status = QueueStatus.WAITING))

        // when
        sut(token)

        // then
        val count = queueJpaRepository.count()
        assertThat(count).isEqualTo(0)
    }
}

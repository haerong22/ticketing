package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class QueueEntityTest {

    @Test
    fun `WaitingQueue 도메인으로 QueueEntity를 생성한다`() {
        // given
        val token = UUID.randomUUID().toString()
        val waitingQueue = WaitingQueue(
            token = token,
            rank = 0,
            status = QueueStatus.WAITING,
        )

        // when
        val result = QueueEntity.of(waitingQueue)

        // then
        assertThat(result.id).isEqualTo(0L)
        assertThat(result.token).isEqualTo(token)
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        assertThat(result.expiredAt).isNull()
    }
}
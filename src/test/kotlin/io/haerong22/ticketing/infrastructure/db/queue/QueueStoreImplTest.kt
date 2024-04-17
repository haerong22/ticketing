package io.haerong22.ticketing.infrastructure.db.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.WaitingQueue
import io.haerong22.ticketing.infrastructure.DbTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import java.util.*

@Import(QueueStoreImpl::class)
class QueueStoreImplTest(
    private val sut: QueueStoreImpl,
) : DbTestSupport() {

    @Test
    fun `대기열 입장`() {
        // given
        val token = UUID.randomUUID().toString()
        val queue = WaitingQueue(
            token = token,
            rank = 0,
            status = QueueStatus.WAITING,
        )

        // when
        val result = sut.enter(queue)

        // then
        assertThat(result.queueId).isEqualTo(1L)
        assertThat(result.token).isEqualTo(token)
        assertThat(result.rank).isEqualTo(0)
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        assertThat(result.expiredAt).isNull()
    }
}
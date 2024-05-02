package io.haerong22.ticketing.application.queue

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.domain.common.enums.QueueStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.RedisTemplate

class EnterWaitingQueueUseCaseTest(
    private val sut: EnterWaitingQueueUseCase,
    private val redisTemplate: RedisTemplate<String, String>,
) : IntegrationTestSupport() {

    @Test
    fun `대기열에 입장한다`() {
        // given
        val waitQueueKey = "queue:wait"

        // when
        val result = sut()

        // then
        val count = redisTemplate.opsForZSet().size(waitQueueKey)
        assertThat(count).isEqualTo(1)

        assertThat(result.rank).isEqualTo(1)
        assertThat(result.token).isNotNull()
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
    }
}

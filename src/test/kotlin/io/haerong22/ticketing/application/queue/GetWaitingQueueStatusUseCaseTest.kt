package io.haerong22.ticketing.application.queue

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.RedisTemplate
import java.util.UUID

class GetWaitingQueueStatusUseCaseTest(
    private val sut: GetWaitingQueueStatusUseCase,
    private val redisTemplate: RedisTemplate<String, String>,
) : IntegrationTestSupport() {

    @Test
    fun `토큰 상태를 조회한다`() {
        // given
        val waitQueueKey = "queue:wait"
        val token = UUID.randomUUID().toString()
        val score = System.currentTimeMillis().toDouble()
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), score)
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), score + 1)
        redisTemplate.opsForZSet().add(waitQueueKey, token, score + 2)

        // when
        val result = sut(token)

        // then
        assertThat(result.rank).isEqualTo(3)
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        assertThat(result.token).isEqualTo(token)
    }

    @Test
    fun `토큰 상태를 조회 시 해당 토큰이 없으면 QueueException 이 발생한다`() {
        // given
        val token = UUID.randomUUID().toString()

        // when, then
        assertThatThrownBy { sut(token) }
            .isInstanceOf(QueueException::class.java)
            .hasMessage("토큰이 없습니다.")
    }
}

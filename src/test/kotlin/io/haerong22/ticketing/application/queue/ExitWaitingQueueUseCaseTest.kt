package io.haerong22.ticketing.application.queue

import io.haerong22.ticketing.application.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.RedisTemplate
import java.util.UUID

class ExitWaitingQueueUseCaseTest(
    private val sut: ExitWaitingQueueUseCase,
    private val redisTemplate: RedisTemplate<String, String>,
) : IntegrationTestSupport() {

    @Test
    fun `대기열 퇴장`() {
        // given
        val waitQueueKey = "queue:wait"
        val token = UUID.randomUUID().toString()
        val score = System.currentTimeMillis().toDouble()
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), score)
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), score + 1)
        redisTemplate.opsForZSet().add(waitQueueKey, token, score + 2)

        // when
        sut(token)

        // then
        val count = redisTemplate.opsForZSet().size(waitQueueKey)
        assertThat(count).isEqualTo(2)
    }
}

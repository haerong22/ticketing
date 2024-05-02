package io.haerong22.ticketing.infrastructure.redis.queue

import io.haerong22.ticketing.infrastructure.RedisTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.RedisTemplate
import java.util.UUID

@Import(QueueRedisRepository::class)
class QueueRedisRepositoryTest(
    private val sut: QueueRedisRepository,
    private val redisTemplate: RedisTemplate<String, String>,
) : RedisTestSupport() {

    @Test
    fun `대기 순번을 조회 한다`() {
        // given
        val waitQueueKey = "queue:wait"
        val token = UUID.randomUUID().toString()
        val score = System.currentTimeMillis().toDouble()
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), score)
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), score + 1)
        redisTemplate.opsForZSet().add(waitQueueKey, token, score + 2)

        // when
        val result = sut.getRank(token)

        // then
        assertThat(result).isEqualTo(3L)
    }

    @Test
    fun `대기 순번을 조회 시 토큰이 없으면 null 을 응답한다`() {
        // given
        val token = UUID.randomUUID().toString()

        // when
        val result = sut.getRank(token)

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `활성 유저 수를 조회한다`() {
        // given
        val proceedQueueKey = "queue:proceed:"
        redisTemplate.opsForValue().set(proceedQueueKey + UUID.randomUUID().toString(), "")
        redisTemplate.opsForValue().set(proceedQueueKey + UUID.randomUUID().toString(), "")
        redisTemplate.opsForValue().set(proceedQueueKey + UUID.randomUUID().toString(), "")

        // when
        val result = sut.getActiveCount()

        // then
        assertThat(result).isEqualTo(3)
    }

    @Test
    fun `대기열 입장한다`() {
        // given
        val token = UUID.randomUUID().toString()

        // when
        val result = sut.enterWaitQueue(token)

        // then
        assertThat(result).isEqualTo(1L)
    }

    @Test
    fun `대기열 퇴장한다`() {
        // given
        val waitQueueKey = "queue:wait"
        val token = UUID.randomUUID().toString()
        redisTemplate.opsForZSet().add(waitQueueKey, token, System.currentTimeMillis().toDouble())

        // when
        sut.exitWaitQueue(token)

        // then
        val result = redisTemplate.opsForZSet().size(waitQueueKey)
        assertThat(result).isEqualTo(0L)
    }

    @Test
    fun `토큰을 활성화 한다`() {
        // given
        val waitQueueKey = "queue:wait"
        val proceedQueueKey = "queue:proceed:*"

        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())

        val count = 2L

        // when
        sut.activeTokens(count)

        // then
        val wait = redisTemplate.opsForZSet().size(waitQueueKey)
        val proceed = redisTemplate.keys(proceedQueueKey).count()

        assertThat(wait).isEqualTo(1L)
        assertThat(proceed).isEqualTo(2L)
    }

    @Test
    fun `토큰의 활성화 여부를 확인한다`() {
        // given
        val proceedQueueKey = "queue:proceed:"
        val activeToken = UUID.randomUUID().toString()
        val inactiveToken = UUID.randomUUID().toString()
        redisTemplate.opsForValue().set(proceedQueueKey + activeToken, activeToken)

        // when
        val active = sut.isProceedToken(activeToken)
        val inactive = sut.isProceedToken(inactiveToken)

        // then
        assertThat(active).isTrue()
        assertThat(inactive).isFalse()
    }
}

package io.haerong22.ticketing.infrastructure.redis.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueStore
import io.haerong22.ticketing.domain.queue.WaitingQueue
import io.haerong22.ticketing.infrastructure.RedisTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.RedisTemplate
import java.util.UUID

@Import(QueueStoreImpl::class, QueueRedisRepository::class)
class QueueStoreImplTest(
    private val sut: QueueStore,
    private val redisTemplate: RedisTemplate<String, String>,
) : RedisTestSupport() {

    @Test
    fun `대기열 등록`() {
        // given
        val waitQueueKey = "queue:wait"

        val queue = WaitingQueue(
            token = UUID.randomUUID().toString(),
            rank = 0,
            status = QueueStatus.WAITING,
        )

        // when
        sut.enter(queue)

        // then
        val result = redisTemplate.opsForZSet().size(waitQueueKey)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `대기열 퇴장`() {
        // given
        val waitQueueKey = "queue:wait"
        val token = UUID.randomUUID().toString()
        redisTemplate.opsForZSet().add(waitQueueKey, token, System.currentTimeMillis().toDouble())

        // when
        sut.exit(token)

        // then
        val result = redisTemplate.opsForZSet().size(waitQueueKey)

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `토큰 활성화`() {
        // given
        val waitQueueKey = "queue:wait"
        val proceedQueueKey = "queue:proceed:*"

        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())
        redisTemplate.opsForZSet().add(waitQueueKey, UUID.randomUUID().toString(), System.currentTimeMillis().toDouble())

        // when
        sut.activeTokens(3)

        // then
        val inactiveCount = redisTemplate.opsForZSet().size(waitQueueKey)
        val activeCount = redisTemplate.keys(proceedQueueKey).size

        assertThat(inactiveCount).isEqualTo(2)
        assertThat(activeCount).isEqualTo(3)
    }
}

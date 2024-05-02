package io.haerong22.ticketing.infrastructure.redis.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueReader
import io.haerong22.ticketing.infrastructure.RedisTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.RedisTemplate
import java.util.UUID

@Import(QueueReaderImpl::class, QueueRedisRepository::class)
class QueueReaderImplTest(
    private val sut: QueueReader,
    private val redisTemplate: RedisTemplate<String, String>,
) : RedisTestSupport() {

    @Test
    fun `토큰 상태를 조회한다(대기 상태)`() {
        // given
        val waitQueueKey = "queue:wait"
        val token = UUID.randomUUID().toString()
        redisTemplate.opsForZSet().add(waitQueueKey, token, System.currentTimeMillis().toDouble())

        // when
        val result = sut.getQueueStatus(token)!!

        assertThat(result.token).isEqualTo(token)
        assertThat(result.rank).isEqualTo(1)
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
    }

    @Test
    fun `토큰 상태를 조회한다(활성 상태)`() {
        // given
        val proceedQueueKey = "queue:proceed:"
        val token = UUID.randomUUID().toString()
        redisTemplate.opsForValue().set(proceedQueueKey + token, token)

        // when
        val result = sut.getQueueStatus(token)!!

        assertThat(result.token).isEqualTo(token)
        assertThat(result.rank).isEqualTo(0)
        assertThat(result.status).isEqualTo(QueueStatus.PROCEEDING)
    }

    @Test
    fun `토큰 상태를 조회시 토큰이 없으면 null 을 응답한다`() {
        // given
        val token = UUID.randomUUID().toString()

        // when
        val result = sut.getQueueStatus(token)

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `활성 상태 토큰 수를 조회한다`() {
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
}

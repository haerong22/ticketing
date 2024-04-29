package io.haerong22.ticketing.infrastructure.redis

import io.haerong22.ticketing.infrastructure.RedisTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.redis.connection.RedisConnectionFactory

class RedisConnectTest(
    private val redisConnectionFactory: RedisConnectionFactory
) : RedisTestSupport() {

    @Test
    fun connect() {
        val conn = redisConnectionFactory.connection
        assertThat(conn).isNotNull
        assertThat(conn.ping()?.uppercase()).isEqualTo("PONG")
    }
}

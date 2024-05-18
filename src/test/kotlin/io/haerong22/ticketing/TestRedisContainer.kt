package io.haerong22.ticketing

import org.testcontainers.containers.GenericContainer

class TestRedisContainer {

    private val REDIS_PORT = 6379
    private val redisContainer = GenericContainer("redis:7.0.8-alpine")
        .withExposedPorts(REDIS_PORT)

    init {
        redisContainer.start()
        System.setProperty("spring.data.redis.host", redisContainer.host)
        System.setProperty("spring.data.redis.port", redisContainer.getMappedPort(REDIS_PORT).toString())
    }
}

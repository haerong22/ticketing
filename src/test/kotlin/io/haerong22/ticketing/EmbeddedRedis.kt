package io.haerong22.ticketing

import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.core.io.ClassPathResource
import redis.embedded.RedisServer
import java.io.File

@TestConfiguration
class EmbeddedRedis(
    @Value("\${spring.data.redis.port}")
    private val port: Int
) {

    private val redisServer: RedisServer =
        if (isArmMac()) {
            RedisServer(port, redisForArmMac())
        } else {
            RedisServer(port)
        }

    init {
        redisServer.start()
    }

    private fun isArmMac(): Boolean {
        return System.getProperty("os.arch") == "aarch64" &&
            System.getProperty("os.name") == "Mac OS X"
    }

    private fun redisForArmMac(): File {
        return ClassPathResource("redis/redis-server-7.2.3-mac-arm64").getFile()
    }

    @PreDestroy
    fun stop() {
        this.redisServer.stop()
    }
}

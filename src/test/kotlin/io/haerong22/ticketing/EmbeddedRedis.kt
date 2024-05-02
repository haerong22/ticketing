package io.haerong22.ticketing

import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import redis.embedded.RedisServer
import java.io.File
import java.net.InetSocketAddress
import java.net.Socket

@Profile("test")
@Configuration
class EmbeddedRedis(
    @Value("\${spring.data.redis.port}")
    private val redisPort: Int
) {
    private val host = "localhost"
    private var port = redisPort

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    private val redisServer: RedisServer =
        if (isArmMac()) {
            RedisServer(getPort(), redisForArmMac())
        } else {
            RedisServer(getPort())
        }

    init {
        redisServer.start()
    }

    private fun getPort(): Int {
        for (p in 10000..65535) {
            if (isAvailablePort(p)) {
                port = p
                return p
            }
        }

        throw IllegalArgumentException("Not Found Available port")
    }

    private fun isAvailablePort(port: Int): Boolean {
        return runCatching {
            Socket().use { socket ->
                socket.connect(InetSocketAddress("localhost", port), 1000)
                false
            }
        }.getOrElse { true }
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

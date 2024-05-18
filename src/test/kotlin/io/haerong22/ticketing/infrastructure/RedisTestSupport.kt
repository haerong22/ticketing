package io.haerong22.ticketing.infrastructure

import io.haerong22.ticketing.TestRedisContainer
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@ActiveProfiles("test")
@DataRedisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
// @Import(EmbeddedRedis::class)
@Import(TestRedisContainer::class)
abstract class RedisTestSupport {

    @Autowired
    lateinit var redisConnectionFactory: RedisConnectionFactory

    @BeforeEach
    fun setup() {
        redisConnectionFactory.connection.commands().flushAll()
    }
}

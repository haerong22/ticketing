package io.haerong22.ticketing.application

import io.haerong22.ticketing.DbCleanup
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@SpringBootTest
abstract class IntegrationTestSupport {

    @Autowired
    lateinit var dbCleanup: DbCleanup

    @Autowired
    lateinit var redisConnectionFactory: RedisConnectionFactory

    @BeforeEach
    fun setup() {
        dbCleanup.execute()
        redisConnectionFactory.connection.commands().flushAll()
    }
}

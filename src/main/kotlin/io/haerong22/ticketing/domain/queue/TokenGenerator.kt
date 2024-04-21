package io.haerong22.ticketing.domain.queue

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TokenGenerator {

    fun generate(): String {
        return UUID.randomUUID().toString()
    }
}

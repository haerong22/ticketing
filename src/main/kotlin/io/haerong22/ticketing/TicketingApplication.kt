package io.haerong22.ticketing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TicketingApplication

fun main(args: Array<String>) {
    runApplication<TicketingApplication>(*args)
}

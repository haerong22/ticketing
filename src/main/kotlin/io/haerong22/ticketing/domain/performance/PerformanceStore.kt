package io.haerong22.ticketing.domain.performance

interface PerformanceStore {

    fun save(seat: Seat) : Seat
}
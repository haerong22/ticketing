package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus

interface ReservationCustomRepository {

    fun findByIdForUpdate(reservationId: Long): ReservationEntity?

    fun updateStatus(ids: List<Long>, status: ReservationStatus)
}

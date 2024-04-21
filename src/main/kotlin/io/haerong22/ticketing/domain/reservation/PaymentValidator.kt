package io.haerong22.ticketing.domain.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.domain.reservation.ReservationResponseCode.NOT_RESERVATION_USER
import io.haerong22.ticketing.domain.reservation.ReservationResponseCode.NOT_RESERVED
import io.haerong22.ticketing.domain.reservation.ReservationResponseCode.RESERVATION_EXPIRED
import io.haerong22.ticketing.domain.user.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PaymentValidator {

    fun validate(user: User, reservation: Reservation) {
        if (user.userId != reservation.userId) {
            throw ReservationException(NOT_RESERVATION_USER)
        }

        if (reservation.status != ReservationStatus.RESERVED) {
            throw ReservationException(NOT_RESERVED)
        }

        if (reservation.expiredAt.isBefore(LocalDateTime.now())) {
            throw ReservationException(RESERVATION_EXPIRED)
        }
    }
}

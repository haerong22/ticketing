package io.haerong22.ticketing.domain.user

import io.haerong22.ticketing.domain.common.enums.TransactionType

class UserPointHistory(
    val amount: Int,
    val type: TransactionType
) {

    companion object {

        fun createChargeHistory(amount: Int) : UserPointHistory {
            return UserPointHistory(amount, TransactionType.CHARGE)
        }
    }

}
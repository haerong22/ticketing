package io.haerong22.ticketing.domain.user

import io.haerong22.ticketing.domain.common.ResponseCode

enum class UserResponseCode(
    override val code: Int,
    override val msg: String,
) : ResponseCode {

    ;
}
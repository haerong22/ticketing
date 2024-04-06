package io.haerong22.ticketing.domain.user

import io.haerong22.ticketing.domain.common.ResponseCode

enum class UserResponseCode(
    override val code: Int,
    override val msg: String,
) : ResponseCode {

    NOT_FOUND_USER(1404, "유저를 찾을 수 없습니다."),

    ;
}
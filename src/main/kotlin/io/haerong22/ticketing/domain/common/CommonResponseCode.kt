package io.haerong22.ticketing.domain.common

enum class CommonResponseCode(
    override val code: Int,
    override val msg: String,
) : ResponseCode {

    SUCCESS(0, "success"),
    BAD_REQUEST(400, "잘못 된 요청 데이터 입니다."),
    UNAUTHORIZED(401, "인증 되지 않은 사용자"),
    ACCESS_DENIED(403, "권한 없음")

    ;
}
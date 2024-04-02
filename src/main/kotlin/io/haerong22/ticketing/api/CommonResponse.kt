package io.haerong22.ticketing.api

import io.haerong22.ticketing.domain.common.CommonErrorCode
import io.haerong22.ticketing.domain.common.ErrorCode

data class CommonResponse<T>(
    var code: Int,
    val message: String,
    val body: T? = null,
) {

    companion object {

        @JvmStatic
        fun <T> ok(): CommonResponse<T> {
            return process(CommonErrorCode.SUCCESS.code, CommonErrorCode.SUCCESS.msg, null)
        }

        @JvmStatic
        fun <T> ok(body: T): CommonResponse<T> {
            return process(0, "success", body)
        }

        @JvmStatic
        fun <T> error(errorCode: ErrorCode): CommonResponse<T> {
            return process(errorCode.code, errorCode.msg, null)
        }

        @JvmStatic
        fun <T> error(errorCode: ErrorCode, message: String): CommonResponse<T> {
            return process(errorCode.code, message, null)
        }

        @JvmStatic
        private fun <T> process(code: Int, message: String, body: T?): CommonResponse<T> {
            return CommonResponse(code, message, body)
        }
    }
}
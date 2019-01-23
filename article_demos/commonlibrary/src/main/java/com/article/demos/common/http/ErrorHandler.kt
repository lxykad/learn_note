package com.article.demos.common.http

import com.article.demos.common.rx.BaseException
/**
 *  @author lxy
 *  @date 2018/10/16
 *  @Description
 */
class ErrorHandler {

    companion object {

        fun getErrorMsg(e: Throwable?): String {
            var msg = "未知错误"

            if (e is BaseException) {
                var base: BaseException = e
                msg = base.msg
            } else {
                if (e.toString().startsWith("java.net")) {
                    msg = "网络错误"
                }
            }
            return msg
        }
    }
}
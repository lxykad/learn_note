package com.article.demos.common.http

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author lxy
 * @date 2018/8
 */
class ParamsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
                .newBuilder()
                .addHeader("Content_Type", "application/json")
                .addHeader("charset", "UTF-8")
                .build()
        return chain.proceed(request)
    }
}
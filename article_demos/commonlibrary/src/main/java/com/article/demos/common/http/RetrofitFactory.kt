package com.article.demos.common.http

import com.article.demos.common.base.BaseApplication
import com.article.demos.common.http.cookie.CookieManager
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author lxy
 * @desc retrofit工厂
 */
class RetrofitFactory private constructor() {

    /**
     * kotlin单例
     */
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val retrofit: Retrofit;
    private var commonInterceptor: Interceptor;

    init {
        commonInterceptor = ParamsInterceptor()
        retrofit = Retrofit.Builder()
                .baseUrl(HttpHelper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkhttpClient())
                .build()
    }

    private fun getOkhttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
                .addInterceptor(getLogInterceptor())
                .addInterceptor(commonInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .cookieJar(CookieManager.getCookiejar())
                .build()
    }

    /**
     * 日志拦截器
     */
    private fun getLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}
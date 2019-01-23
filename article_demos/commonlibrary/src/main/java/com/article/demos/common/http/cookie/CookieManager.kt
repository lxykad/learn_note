package com.article.demos.common.http.cookie

import com.article.demos.common.base.BaseApplication
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

class CookieManager {

    companion object {
        var cookieJar: ClearableCookieJar? = null
        fun getCookiejar(): ClearableCookieJar? {
            if (cookieJar == null) {
                cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(BaseApplication.sContext))
            }
            return cookieJar
        }
    }
}
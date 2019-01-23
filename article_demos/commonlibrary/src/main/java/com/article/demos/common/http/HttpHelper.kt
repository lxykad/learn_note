package com.article.demos.common.http

/**
 * @author lxy
 * @date 2018/8
 * @desc kotlin的静态变量和静态代码块
 */
object HttpHelper {

    var BASE_URL: String;

    init {
        BASE_URL = "http://www.wanandroid.com/"
    }

}
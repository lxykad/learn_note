package com.article.demos.common.base

/**
 *  @author lxy
 *  @date 2018/8/21
 *  @Description
 */
data class BaseBean<T>(
        val errorCode: Int,
        val errorMsg: String,
        val data: T
)
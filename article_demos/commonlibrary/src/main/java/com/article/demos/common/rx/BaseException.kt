package com.article.demos.common.rx

/**
 *  @author lxy
 *  @date 2018/8/23
 *  @Description 自定义异常
 */
class BaseException(val code: Int, val msg: String) : Throwable()
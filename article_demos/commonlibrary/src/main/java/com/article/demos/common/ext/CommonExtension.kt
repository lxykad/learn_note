package com.article.demos.common.ext

import android.content.Context
import android.widget.Toast

/**
 *  @Description 通用的扩展方法
 */

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

/**
 *  @Description 扩展RetrofitFactory
 */

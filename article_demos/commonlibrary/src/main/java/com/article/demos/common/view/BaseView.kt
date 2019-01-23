package com.article.demos.common.view

interface BaseView {

    fun showLoading()
    fun dismissLoading()
    fun showError(msg: String)
}
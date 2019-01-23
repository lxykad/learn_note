package com.article.demos.common.rx

import com.article.demos.common.http.ErrorHandler
import com.article.demos.common.view.BaseView
import io.reactivex.disposables.Disposable

/**
 *  @author lxy
 *  @date 2018/8/23
 *  @Description
 */
abstract class ProgressObserver<T>(var baseView: BaseView, private val showLoading: Boolean) : BaseObserver<T>() {

    override fun onError(e: Throwable) {
        if (showLoading) {
            baseView.dismissLoading()
        }
        baseView.showError(ErrorHandler.getErrorMsg(e))
    }

    override fun onComplete() {
        if (showLoading) {
            baseView.dismissLoading()
        }
    }

    override fun onSubscribe(d: Disposable) {
        if (showLoading) {
            baseView.showLoading()
        }
    }
}
package com.article.demos.common.rx

import com.article.demos.common.base.BaseBean
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 *  @author liuxinyu
 *  @date 2018/8/21
 *  @Description 通用数据转换类
 */
object RxHttpResponse {

    fun <T> handResult(): ObservableTransformer<BaseBean<T>, T> {

        return ObservableTransformer<BaseBean<T>, T>() { upstream ->

            upstream.flatMap(Function<BaseBean<T>, ObservableSource<T>>() { basebean ->

                if (basebean.errorCode == 0) {
                    Observable.create {
                        if (basebean.data != null) {
                            it.onNext(basebean.data)
                        }
                        it.onComplete()
                    }
                } else {
                    var msg: String = basebean.errorMsg
                    var code = basebean.errorCode
                    Observable.error(BaseException(code, msg))
                }

            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

        }
    }
}
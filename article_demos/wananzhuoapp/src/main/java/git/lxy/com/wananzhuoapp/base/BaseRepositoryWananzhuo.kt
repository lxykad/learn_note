package git.lxy.com.wananzhuoapp.base

import com.article.demos.common.http.RetrofitFactory
import git.lxy.com.wananzhuoapp.service.WanAndroidApiService
import io.reactivex.disposables.CompositeDisposable

/**
 *  @author lxy
 *  @date 2018/9/20
 *  @Description
 */
open class BaseRepositoryWananzhuo {

    protected var apiService: WanAndroidApiService
    protected var compos: CompositeDisposable

    init {
        apiService = RetrofitFactory.instance.create(WanAndroidApiService::class.java)
        compos = CompositeDisposable()
    }

    fun sub(disposable: CompositeDisposable) {
        compos?.add(disposable)
    }

    fun unSub() {
        compos?.let {
            if (it.size() > 0) {
                it.clear()
            }
        }
    }
}
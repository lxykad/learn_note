package git.lxy.com.wananzhuoapp.main

import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.http.ErrorHandler
import com.article.demos.common.rx.ProgressObserver
import com.article.demos.common.rx.RxHttpResponse
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseRepositoryWananzhuo
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 *  @author lxy
 *  @Description
 */
class res : BaseRepositoryWananzhuo() {

    fun getHomeArticleList(baseView: BaseView, page: Int, showLoading: Boolean): MediatorLiveData<Data> {

        var liveData: MediatorLiveData<Data> = MediatorLiveData()

        apiService
                .getHomeArticleList(page)
                .compose(RxHttpResponse.handResult())
                .subscribe(object : Observer<Data> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: Data?) {
                        liveData.value = t
                    }

                    override fun onError(e: Throwable?) {
                        liveData.value = null
                        baseView.showError(ErrorHandler.getErrorMsg(e))
                    }

                })

        return liveData
    }

    fun getBanner(baseView: BaseView, showLoading: Boolean): MediatorLiveData<List<BannerBean>> {

        var liveData: MediatorLiveData<List<BannerBean>> = MediatorLiveData()

        apiService
                .getHomeBanner()
                .compose(RxHttpResponse.handResult())
                .subscribe(object : ProgressObserver<List<BannerBean>>(baseView, showLoading) {
                    override fun onNext(value: List<BannerBean>?) {
                        liveData.value = value
                    }
                })
        return liveData
    }
}
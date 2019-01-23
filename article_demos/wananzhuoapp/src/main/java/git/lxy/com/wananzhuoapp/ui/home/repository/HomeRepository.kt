package git.lxy.com.wananzhuoapp.ui.home.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.base.BaseBean
import com.article.demos.common.http.ErrorHandler
import com.article.demos.common.http.RetrofitFactory
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.service.WanAndroidApiService
import git.lxy.com.wananzhuoapp.ui.home.CollectBean
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeRepository {

    companion object {

        fun collectArticle(articleId: Int, baseView: BaseView): LiveData<String> {
            var liveData = MediatorLiveData<String>()
            RetrofitFactory.instance
                    .create(WanAndroidApiService::class.java)
                    .collectArticle(articleId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<BaseBean<CollectBean>> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable?) {
                        }

                        override fun onNext(t: BaseBean<CollectBean>?) {
                            t?.let {
                                if (t.errorCode == 0) {
                                    liveData.value = "success"
                                } else {
                                    baseView.showError(t.errorMsg)
                                }
                            }
                        }

                        override fun onError(e: Throwable?) {
                            baseView.showError(ErrorHandler.getErrorMsg(e))
                        }

                    })

            return liveData
        }

        fun cancelCollectArticle(articleId: Int, baseView: BaseView): LiveData<String> {

            var liveData = MediatorLiveData<String>()
            RetrofitFactory.instance
                    .create(WanAndroidApiService::class.java)
                    .unCollectArticle(articleId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<BaseBean<CollectBean>> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable?) {
                        }

                        override fun onNext(t: BaseBean<CollectBean>?) {
                            t?.let {
                                if (t.errorCode == 0) {
                                    liveData.value = "success"
                                } else {
                                    baseView.showError(t.errorMsg)
                                }
                            }
                        }

                        override fun onError(e: Throwable?) {
                            baseView.showError(ErrorHandler.getErrorMsg(e))
                        }

                    })

            return liveData
        }
    }
}
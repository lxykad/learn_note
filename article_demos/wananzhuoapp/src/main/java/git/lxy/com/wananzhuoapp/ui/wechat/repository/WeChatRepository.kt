package git.lxy.com.wananzhuoapp.ui.wechat.repository

import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.http.ErrorHandler
import com.article.demos.common.rx.ProgressObserver
import com.article.demos.common.rx.RxHttpResponse
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseRepositoryWananzhuo
import git.lxy.com.wananzhuoapp.ui.wechat.bean.WeChatArticle
import git.lxy.com.wananzhuoapp.ui.wechat.bean.WeChatListBean
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeChatRepository : BaseRepositoryWananzhuo() {

    /**
     * 获取公众号列表
     */
    fun getWeChatList(baseView: BaseView, showLoading: Boolean): MediatorLiveData<MutableList<WeChatListBean>> {
        var liveData: MediatorLiveData<MutableList<WeChatListBean>> = MediatorLiveData()

        apiService
                .getWeChatList()
                .compose(RxHttpResponse.handResult())
                .subscribe(object : ProgressObserver<MutableList<WeChatListBean>>(baseView, true) {
                    override fun onNext(value: MutableList<WeChatListBean>?) {
                        liveData.value = value
                    }
                })

        return liveData
    }

    /**
     * 获取公众号历史文章
     */
    fun getArticleList(baseView: BaseView, showLoading: Boolean, id: Int, page: Int): MediatorLiveData<WeChatArticle> {
        var liveData: MediatorLiveData<WeChatArticle> = MediatorLiveData()

        apiService
                .getWeChatHistoryList(id, page)
                .compose(RxHttpResponse.handResult())
                .subscribe(object : Observer<WeChatArticle> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: WeChatArticle?) {
                        liveData.value = t

                    }

                    override fun onError(e: Throwable?) {
                        liveData.value = null
                        baseView.showError(ErrorHandler.getErrorMsg(e))
                    }

                })
        return liveData
    }
}

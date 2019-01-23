package git.lxy.com.wananzhuoapp.ui.wechat.vm

import android.arch.lifecycle.*
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseViewModelWananzhuo
import git.lxy.com.wananzhuoapp.ui.my.event.RefreshCollectEvent
import git.lxy.com.wananzhuoapp.ui.wechat.bean.WeChatArticle
import git.lxy.com.wananzhuoapp.ui.wechat.repository.WeChatRepository
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 *  @author lxy
 *  @date 2018/10/16
 *  @Description
 */
class WeChatArticleViewModel : BaseViewModelWananzhuo<WeChatRepository>(), LifecycleObserver {

    var refreshLiveData = MutableLiveData<Int>()


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        if (!EventBus.getDefault().isRegistered(this@WeChatArticleViewModel)) {
            EventBus.getDefault().register(this@WeChatArticleViewModel)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        if (EventBus.getDefault().isRegistered(this@WeChatArticleViewModel)) {
            EventBus.getDefault().unregister(this@WeChatArticleViewModel)
        }
    }

    @Subscribe
    fun onReceiveLoginEvent(event: RefreshCollectEvent) {
        if (event.value == RefreshCollectEvent.TYPE_REFRESH) {
            refreshLiveData.value = event.value
        }
    }

    fun getWeChatArticleHistory(showLoading: Boolean, baseView: BaseView, id: Int, page: Int): MediatorLiveData<WeChatArticle>? {
        return mRepository?.getArticleList(baseView, showLoading, id, page)
    }
}
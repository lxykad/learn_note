package git.lxy.com.wananzhuoapp.ui.home.vm

import android.arch.lifecycle.*
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseViewModelWananzhuo
import git.lxy.com.wananzhuoapp.main.BannerBean
import git.lxy.com.wananzhuoapp.main.Data
import git.lxy.com.wananzhuoapp.main.res
import git.lxy.com.wananzhuoapp.ui.home.repository.HomeRepository
import git.lxy.com.wananzhuoapp.ui.my.event.RefreshCollectEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 *  @author lxy
 *  @Description
 */
class HomeViewModel : BaseViewModelWananzhuo<res>(), LifecycleObserver {

    var refreshLiveData = MutableLiveData<Int>()

    fun loadData(baseView: BaseView, page: Int, showLoading: Boolean): MediatorLiveData<Data>? {
        return mRepository?.getHomeArticleList(baseView, page, showLoading)
    }

    fun getBanner(baseView: BaseView, showLoading: Boolean): MediatorLiveData<List<BannerBean>>? {
        return mRepository?.getBanner(baseView, showLoading)
    }

    fun clickLike(articleId: Int, baseView: BaseView): LiveData<String> {
        return HomeRepository.collectArticle(articleId, baseView)
    }

    fun clickUnLike(articleId: Int, baseView: BaseView): LiveData<String> {
        return HomeRepository.cancelCollectArticle(articleId, baseView)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        if (!EventBus.getDefault().isRegistered(this@HomeViewModel)) {
            EventBus.getDefault().register(this@HomeViewModel)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        if (EventBus.getDefault().isRegistered(this@HomeViewModel)) {
            EventBus.getDefault().unregister(this@HomeViewModel)
        }
    }

    @Subscribe
    fun onReceiveLoginEvent(event: RefreshCollectEvent) {
        if (event.value == RefreshCollectEvent.TYPE_REFRESH) {
            refreshLiveData.value = event.value
        }
    }
}
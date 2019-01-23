package git.lxy.com.wananzhuoapp.ui.my.vm

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseViewModelWananzhuo
import git.lxy.com.wananzhuoapp.ui.my.entity.CollectData
import git.lxy.com.wananzhuoapp.ui.my.repository.MyRepository

/**
 *  @author lxy
 *  @date 2018/10/18
 *  @Description
 */
class CollectViewModel : BaseViewModelWananzhuo<MyRepository>(), LifecycleObserver {

    fun getCollect(baseView: BaseView, page: Int): MediatorLiveData<CollectData>? {
        return mRepository?.getCollectList(page, baseView)
    }

    fun unCollect(id: Int, originId: Int, baseView: BaseView): LiveData<String>? {
        return mRepository?.cancelCollect(id, originId, baseView)
    }
}
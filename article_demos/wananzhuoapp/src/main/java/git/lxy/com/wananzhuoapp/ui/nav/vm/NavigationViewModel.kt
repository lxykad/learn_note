package git.lxy.com.wananzhuoapp.ui.nav.vm

import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseViewModelWananzhuo
import git.lxy.com.wananzhuoapp.ui.nav.entity.NavigationData
import git.lxy.com.wananzhuoapp.ui.nav.repository.NavigationRepository

/**
 *  @author liuxinyu
 *  @date 2018/9/21
 *  @Description
 */
class NavigationViewModel : BaseViewModelWananzhuo<NavigationRepository>() {

    fun getNaviList(baseView: BaseView, showLoading: Boolean): MediatorLiveData<List<NavigationData>>? {
        return mRepository?.getData(baseView,showLoading)
    }
}
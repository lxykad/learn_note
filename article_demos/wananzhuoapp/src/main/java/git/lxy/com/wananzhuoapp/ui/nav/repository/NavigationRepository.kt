package git.lxy.com.wananzhuoapp.ui.nav.repository

import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.rx.ProgressObserver
import com.article.demos.common.rx.RxHttpResponse
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseRepositoryWananzhuo
import git.lxy.com.wananzhuoapp.ui.nav.entity.NavigationData

/**
 *  @author liuxinyu
 *  @date 2018/9/21
 *  @Description
 */
class NavigationRepository : BaseRepositoryWananzhuo() {

    fun getData(baseView: BaseView, showLoading: Boolean): MediatorLiveData<List<NavigationData>> {
        var liveData = MediatorLiveData<List<NavigationData>>()
        apiService
                .getNavData()
                .compose(RxHttpResponse.handResult())
                .subscribe(object : ProgressObserver<List<NavigationData>>(baseView, showLoading) {
                    override fun onNext(value: List<NavigationData>?) {
                        liveData.value = value
                    }
                })
        return liveData
    }
}
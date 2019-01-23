package git.lxy.com.wananzhuoapp.ui.tree.repository

import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.rx.ProgressObserver
import com.article.demos.common.rx.RxHttpResponse
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseRepositoryWananzhuo
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeChildBean
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeEntity

/**
 *  @author liuxinyu
 *  @date 2018/9/5
 *  @Description
 */
class TreeRepository : BaseRepositoryWananzhuo() {

    /**
     * 知识体系页面
     */
    fun getTreeData(baseView: BaseView): MediatorLiveData<MutableList<TreeEntity>> {

        var liveData: MediatorLiveData<MutableList<TreeEntity>> = MediatorLiveData()

        apiService
                .getKnowledgeTree()
                .compose(RxHttpResponse.handResult())
                .subscribe(object : ProgressObserver<MutableList<TreeEntity>>(baseView, true) {
                    override fun onNext(t: MutableList<TreeEntity>) {
                        liveData.value = t
                    }
                })

        return liveData
    }

    /**
     * 知识体系二级页面
     */
    fun getTreeChildArticleList(baseView: BaseView, page: Int, cid: Int, showLoading: Boolean): MediatorLiveData<TreeChildBean> {
        var liveData = MediatorLiveData<TreeChildBean>()

        apiService
                .getTreeChildList(page, cid)
                .compose(RxHttpResponse.handResult())
                .subscribe(object : ProgressObserver<TreeChildBean>(baseView, showLoading) {
                    override fun onNext(t: TreeChildBean) {
                        liveData.value = t
                    }
                })

        return liveData
    }
}
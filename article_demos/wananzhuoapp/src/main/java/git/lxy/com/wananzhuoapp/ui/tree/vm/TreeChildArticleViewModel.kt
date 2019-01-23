package git.lxy.com.wananzhuoapp.ui.tree.vm

import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseViewModelWananzhuo
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeChildBean
import git.lxy.com.wananzhuoapp.ui.tree.repository.TreeRepository

class TreeChildArticleViewModel : BaseViewModelWananzhuo<TreeRepository>() {

    fun getArticleList(cid: Int, page: Int, showLoading: Boolean, baseView: BaseView):
            MediatorLiveData<TreeChildBean>? {
        return mRepository?.getTreeChildArticleList(baseView, page, cid, showLoading)
    }
}
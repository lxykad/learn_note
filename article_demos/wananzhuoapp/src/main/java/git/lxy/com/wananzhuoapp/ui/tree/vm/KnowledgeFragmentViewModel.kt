package git.lxy.com.wananzhuoapp.ui.tree.vm

import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseViewModelWananzhuo
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeEntity
import git.lxy.com.wananzhuoapp.ui.tree.repository.TreeRepository

/**
 *  @author lxy
 *  @date 2018/9/5
 *  @Description
 */
class KnowledgeFragmentViewModel : BaseViewModelWananzhuo<TreeRepository>() {

    fun getTreeData(baseView: BaseView): MediatorLiveData<MutableList<TreeEntity>>? {

        return mRepository?.getTreeData(baseView)
    }
}
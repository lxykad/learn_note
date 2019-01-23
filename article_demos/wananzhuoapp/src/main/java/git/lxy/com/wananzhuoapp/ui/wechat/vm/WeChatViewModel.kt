package git.lxy.com.wananzhuoapp.ui.wechat.vm

import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseViewModelWananzhuo
import git.lxy.com.wananzhuoapp.ui.wechat.bean.WeChatListBean
import git.lxy.com.wananzhuoapp.ui.wechat.repository.WeChatRepository

class WeChatViewModel : BaseViewModelWananzhuo<WeChatRepository>() {

    fun getWeChatList(showLoading: Boolean, baseView: BaseView): MediatorLiveData<MutableList<WeChatListBean>>? {
        return mRepository?.getWeChatList(baseView, showLoading)
    }
}
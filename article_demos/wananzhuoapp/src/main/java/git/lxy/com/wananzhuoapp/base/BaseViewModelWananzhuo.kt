package git.lxy.com.wananzhuoapp.base

import android.arch.lifecycle.ViewModel
import git.lxy.com.wananzhuoapp.util.TUtil

open class BaseViewModelWananzhuo<T : BaseRepositoryWananzhuo> : ViewModel() {

    var mRepository: T? = null

    init {
        mRepository ?: TUtil.getNewInstance<T>(this, 0).also {
            mRepository = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        mRepository?.unSub()
    }
}
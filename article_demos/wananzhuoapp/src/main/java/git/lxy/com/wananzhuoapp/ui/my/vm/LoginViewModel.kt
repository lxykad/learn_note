package git.lxy.com.wananzhuoapp.ui.my.vm

import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseViewModelWananzhuo
import git.lxy.com.wananzhuoapp.ui.my.entity.UserEntity
import git.lxy.com.wananzhuoapp.ui.my.repository.MyRepository
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 *  @author lxy
 *  @date 2018/9/6
 *  @Description
 */
class LoginViewModel : BaseViewModelWananzhuo<MyRepository>() {

    fun login(name: String, pwd: String, baseView: BaseView): MediatorLiveData<UserEntity>? {
        return mRepository?.login(name, pwd, baseView)
    }

    fun register(name: String, pwd: String, baseView: BaseView): MediatorLiveData<UserEntity>? {
        return mRepository?.register(name, pwd, baseView)
    }

    fun getUserFromDatabase(): Flowable<UserEntity>? {
        return mRepository?.getUserFromDb()
    }

    fun updateUserInfo(userEntity: UserEntity): Completable {
        return Completable.fromAction {
            mRepository?.updateUserInfo(userEntity)
        }
    }

    fun delUserInfo(): Completable {
        return Completable.fromAction {
            mRepository?.delUserInfo()
        }
    }
}
package git.lxy.com.wananzhuoapp.ui.my.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.article.demos.common.base.BaseBean
import com.article.demos.common.http.ErrorHandler
import com.article.demos.common.rx.ProgressObserver
import com.article.demos.common.rx.RxHttpResponse
import com.article.demos.common.view.BaseView
import git.lxy.com.wananzhuoapp.base.BaseRepositoryWananzhuo
import git.lxy.com.wananzhuoapp.ui.home.CollectBean
import git.lxy.com.wananzhuoapp.ui.my.entity.CollectData
import git.lxy.com.wananzhuoapp.ui.my.entity.UserEntity
import git.lxy.com.wananzhuoapp.ui.my.entity.db.UserDataBase
import io.reactivex.Flowable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyRepository : BaseRepositoryWananzhuo() {

    fun register(name: String, pwd: String, baseView: BaseView): MediatorLiveData<UserEntity> {
        var liveData = MediatorLiveData<UserEntity>()

        apiService?.let {
            it
                    .register(name, pwd, pwd)
                    .compose(RxHttpResponse.handResult())
                    .subscribe(object : ProgressObserver<UserEntity>(baseView, true) {
                        override fun onNext(t: UserEntity) {
                            liveData.value = t
                        }
                    })
        }
        return liveData
    }

    fun login(name: String, pwd: String, baseView: BaseView): MediatorLiveData<UserEntity> {

        var liveData = MediatorLiveData<UserEntity>()

        apiService?.let {
            it.login(name, pwd)
                    .compose(RxHttpResponse.handResult())
                    .subscribe(object : ProgressObserver<UserEntity>(baseView, true) {
                        override fun onNext(t: UserEntity) {
                            liveData.value = t
                        }
                    })
        }
        return liveData
    }

    fun getUserFromDb(): Flowable<UserEntity> {
        return UserDataBase.getInstance().userDao().getUser2()
    }

    fun updateUserInfo(userEntity: UserEntity) {
        val userDao = UserDataBase.getInstance().userDao()
        userDao.insertUser(userEntity)
    }

    fun delUserInfo() {
        val userDao = UserDataBase.getInstance().userDao()
        userDao.delAllUsers()
    }

    fun getCollectList(page: Int, baseView: BaseView): MediatorLiveData<CollectData> {
        var liveData: MediatorLiveData<CollectData> = MediatorLiveData()

        apiService
                .getCollectList(page)
                .compose(RxHttpResponse.handResult())
                .subscribe(object : Observer<CollectData> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: CollectData?) {
                        liveData.value = t
                    }

                    override fun onError(e: Throwable?) {
                        liveData.value = null
                        baseView.showError(ErrorHandler.getErrorMsg(e))
                    }

                })
        return liveData
    }

    /**
     * 我的收藏页面--取消收藏
     */
    fun cancelCollect(articleId: Int,originId:Int, baseView: BaseView): LiveData<String> {
        var liveData = MediatorLiveData<String>()

        apiService.cancelMyCollect(articleId,originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseBean<CollectBean>> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: BaseBean<CollectBean>?) {
                        liveData.value = "success"
                    }

                    override fun onError(e: Throwable?) {
                        baseView.showError(ErrorHandler.getErrorMsg(e))
                    }

                })

        return liveData
    }
}

package git.lxy.com.wananzhuoapp.ui.my.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.article.demos.common.base.BaseFragment
import com.article.demos.common.http.cookie.CookieManager
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.databinding.WananzhuoFragmentMyBinding
import git.lxy.com.wananzhuoapp.ui.my.activity.LoginActivity
import git.lxy.com.wananzhuoapp.ui.my.activity.MyCollectActivity
import git.lxy.com.wananzhuoapp.ui.my.entity.UserEntity
import git.lxy.com.wananzhuoapp.ui.my.vm.LoginViewModel
import git.lxy.com.wananzhuoapp.ui.my.vm.MyFragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.wananzhuo_fragment_my.*

/**
 *  @author lxy
 *  @date 2018/8/23
 *  @Description
 */
class MyFragment : BaseFragment() {

    lateinit var mBinding: WananzhuoFragmentMyBinding
    lateinit var mViewModel: MyFragmentViewModel
    private var mDisposable = CompositeDisposable()
    lateinit var mLoginViewModel: LoginViewModel
    private var mHasLogin: Boolean = false
    lateinit var userEntity: UserEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.wananzhuo_fragment_my, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MyFragmentViewModel::class.java)
        lifecycle.addObserver(mViewModel)
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun firstVisiableToUser() {
        setUserName()
        tvLoginOut.setOnClickListener { loginOut() }
        tvMyCollect.setOnClickListener { clickCollect() }

        mViewModel.loginLiveData.observe(this, Observer {
            setUserName()
        })
    }

    fun setUserName() {
        mDisposable.add(
                mLoginViewModel.getUserFromDatabase()
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe {
                            it?.let {
                                tvName?.setText(it.username)
                                mHasLogin = true
                                userEntity = it
                                tvLoginOut?.setText("退出登录")
                            }
                        }
        )
    }

    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }

    fun loginOut() {

        when (mHasLogin) {
            true -> {
                mDisposable.add(
                        mLoginViewModel.delUserInfo()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    tvLoginOut?.setText("点击登录")
                                    mHasLogin = false
                                    tvName?.setText("")
                                    CookieManager.getCookiejar()?.clear()
                                }
                )
            }
            false -> {
                Intent().run {
                    startActivity(setClass(context, LoginActivity::class.java))
                }
            }
        }
    }

    fun clickCollect() {
        when (mHasLogin) {
            true -> {
                MyCollectActivity.openActivity(context)
            }
            false -> {
                Intent().run {
                    startActivity(setClass(context, LoginActivity::class.java))
                }
            }
        }
    }
}

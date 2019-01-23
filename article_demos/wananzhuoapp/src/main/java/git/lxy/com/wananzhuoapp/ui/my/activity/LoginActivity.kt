package git.lxy.com.wananzhuoapp.ui.my.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.article.demos.common.base.BaseActivity
import com.article.demos.common.ext.toast
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.ui.my.event.LoginEvent
import git.lxy.com.wananzhuoapp.ui.my.vm.LoginViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.wananzhuo_activity_login.*
import org.greenrobot.eventbus.EventBus

/**
 *  @author lxy
 *  @date 2018/9/6
 *  @Description
 */
class LoginActivity : BaseActivity() {

    lateinit var mLoginViewModel: LoginViewModel
    private var mDisposable = CompositeDisposable()

    override fun getLayoutId() = R.layout.wananzhuo_activity_login

    override fun init() {
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        btRegister.setOnClickListener {
            mLoginViewModel.register(etUsername.text.toString().trim(), etPassword.text.toString().trim(), this)
                    ?.observeForever {
                        toast("注册成功")
                    }
        }

        btLogin.setOnClickListener {
            mLoginViewModel.login(etUsername.text.toString().trim(), etPassword.text.toString().trim(), this)
                    ?.observe(this, Observer {
                        it?.let {
                            toast("登录成功")
                            mDisposable.add(
                                    mLoginViewModel.updateUserInfo(it)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe {
                                                this@LoginActivity.onBackPressed()
                                                EventBus.getDefault().post(LoginEvent(LoginEvent.TYPE_LOGIN))
                                            }
                            )
                        }
                    })
        }
    }

    override fun onStart() {
        super.onStart()
        setHintName()
    }

    fun setHintName() {
        mDisposable.add(mLoginViewModel.getUserFromDatabase()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe() {
                    etUsername.setText(it?.username)
                }
        )
    }
}
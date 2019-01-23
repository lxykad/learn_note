package git.lxy.com.wananzhuoapp.ui.my.vm

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.article.demos.common.base.BaseViewModel
import git.lxy.com.wananzhuoapp.ui.my.event.LoginEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MyFragmentViewModel : BaseViewModel(), LifecycleObserver {

    var loginLiveData = MutableLiveData<Int>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        if (!EventBus.getDefault().isRegistered(this@MyFragmentViewModel)) {
            EventBus.getDefault().register(this@MyFragmentViewModel)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        if (EventBus.getDefault().isRegistered(this@MyFragmentViewModel)) {
            EventBus.getDefault().unregister(this@MyFragmentViewModel)
        }
    }

    @Subscribe
    fun onReceiveLoginEvent(event: LoginEvent) {
        if (event.value == LoginEvent.TYPE_LOGIN) {
            loginLiveData.value = event.value
        }
    }
}
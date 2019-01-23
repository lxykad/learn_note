package git.lxy.com.wananzhuoapp.main.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 *  @author lxy
 *  @Description vm 可以用于act里多个fragment的数据共享
 */
class WanAndroidMainViewModel : ViewModel() {

    private var mainData: MutableLiveData<String> = MutableLiveData()

    fun setData() {
        mainData.value = "main,haha"
    }

    fun getData(): MutableLiveData<String> {
        return mainData
    }
}
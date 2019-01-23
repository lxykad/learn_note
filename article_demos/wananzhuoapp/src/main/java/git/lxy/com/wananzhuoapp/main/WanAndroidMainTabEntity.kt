package git.lxy.com.wananzhuoapp.main

import com.flyco.tablayout.listener.CustomTabEntity

class WanAndroidMainTabEntity(val title: String, val selectedIcon: Int, val unSelectedIcon: Int) : CustomTabEntity {

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }

}
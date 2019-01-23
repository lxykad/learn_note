package git.lxy.com.wananzhuoapp.main

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.article.demos.common.base.BaseActivity
import com.article.demos.common.constant.Constant
import com.flyco.tablayout.listener.CustomTabEntity
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.main.vm.WanAndroidMainViewModel
import git.lxy.com.wananzhuoapp.ui.tree.fragment.KnowledgeFragment
import git.lxy.com.wananzhuoapp.ui.my.fragment.MyFragment
import git.lxy.com.wananzhuoapp.ui.nav.NavigationFragment
import git.lxy.com.wananzhuoapp.ui.home.HomeFragment
import git.lxy.com.wananzhuoapp.ui.wechat.fragment.WeChatFragment
import kotlinx.android.synthetic.main.activity_wananzhuo_main.*

@Route(path = Constant.WAN_ANDROID_MAIN)
class MainActivity : BaseActivity() {

    private val mList = ArrayList<Fragment>()
    private val mTabList = ArrayList<CustomTabEntity>()

    private lateinit var mTitles: Array<String>
    private val mIconSelectIds = intArrayOf(R.drawable.wanandroid_ic_home_selected_24dp, R.drawable.wanandroid_ic_knowledge_selected, R.drawable.wanandroid_ic_navigation_selected, R.drawable.wananzhuo_ic_wechat_selected, R.drawable.wanandroid_ic_my_selected)
    private val mIconUnselectIds = intArrayOf(R.drawable.wanandroid_ic_home_unselected_24dp, R.drawable.wanandroid_ic_knowledge_unselected, R.drawable.wanandroid_ic_navigation_unselected, R.drawable.wananzhuo_ic_wechat_unselect, R.drawable.wanandroid_ic_my_unselected)

    lateinit var mViewModel: WanAndroidMainViewModel

    override fun getLayoutId() = R.layout.activity_wananzhuo_main
    override fun init() {
        mViewModel = ViewModelProviders.of(this).get(WanAndroidMainViewModel::class.java)
        initData()
    }

    private fun initData() {
        mTitles = resources.getStringArray(R.array.wanandroid_home_tab)

        mList.add(HomeFragment())
        mList.add(KnowledgeFragment())
        mList.add(NavigationFragment())
        mList.add(WeChatFragment())
        mList.add(MyFragment())

        for (i in mTitles.indices) {
            mTabList.add(WanAndroidMainTabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }
        tab_layout.setTabData(mTabList, this, R.id.frame_layout, mList)

        mViewModel.setData()
    }
}

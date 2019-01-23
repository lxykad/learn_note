package git.lxy.com.wananzhuoapp.ui.wechat.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.article.demos.common.base.BaseFragment
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.ui.wechat.vm.WeChatViewModel
import kotlinx.android.synthetic.main.wananzhuo_fragment_wechat.*
import java.util.*

/**
 *  @author lxy
 *  @date 2018/10/15
 *  @Description
 */
class WeChatFragment : BaseFragment() {

    lateinit var weChatViewModel: WeChatViewModel
    lateinit var mTitles: Array<String?>
    lateinit var mFragments: ArrayList<Fragment>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wananzhuo_fragment_wechat, container, false)
    }

    override fun firstVisiableToUser() {
        weChatViewModel = ViewModelProviders.of(this).get(WeChatViewModel::class.java)
        mFragments = arrayListOf()
        loadWeChatList()
    }

    fun loadWeChatList() {

        weChatViewModel.getWeChatList(true, this)
                ?.observe(this, Observer {
                    it?.let {
                        mTitles = arrayOfNulls<String>(it.size)
                        it.forEachIndexed { index, bean ->
                            mTitles.set(index, bean.name)
                            mFragments.add(WeChatListFragment.newInstance(bean.id))
                        }
                        slidingTabLayout.setViewPager(viewPager, mTitles, activity, mFragments)
                    }
                })
    }
}
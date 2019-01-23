package git.lxy.com.wananzhuoapp.ui.tree.activity

import android.content.Context
import android.content.Intent
import com.article.demos.common.base.BaseActivity
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.ui.tree.adapter.TreeTabAdapter
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeEntity
import kotlinx.android.synthetic.main.wananzhuo_activity_tree_child.*

/**
 *  @author liuxinyu
 *  @date 2018/9/5
 *  @Description
 */
class TreeChildActivity : BaseActivity() {

    lateinit var mTreeBean: TreeEntity
    lateinit var mTabAdapter: TreeTabAdapter

    companion object {
        var CHILD_LIST = "child_list"

        fun openActivity(context: Context?, bean: TreeEntity) {
            Intent(context, TreeChildActivity::class.java).let {
                it.putExtra(CHILD_LIST, bean)
                context?.startActivity(it)
            }
        }
    }

    override fun getLayoutId() = R.layout.wananzhuo_activity_tree_child

    override fun init() {
        intent.getSerializableExtra(CHILD_LIST)?.let {
            mTreeBean = it as TreeEntity
        }
        mTabAdapter = TreeTabAdapter(mTreeBean.children, supportFragmentManager)
        viewPager.adapter = mTabAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
package git.lxy.com.wananzhuoapp.ui.tree.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeChild
import git.lxy.com.wananzhuoapp.ui.tree.fragment.TreeChildFragment

/**
 *  @author lxy
 *  @date 2018/9/5
 *  @Description
 */
class TreeTabAdapter(var list: List<TreeChild>, fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private var fragments = mutableListOf<Fragment>()

    init {
        list.forEach {
            fragments.add(TreeChildFragment.newInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return list[position].name
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
        // super.destroyItem(container, position, `object`)
    }
}
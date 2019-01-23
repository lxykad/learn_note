package git.lxy.com.wananzhuoapp.ui.nav

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.article.demos.common.base.BaseFragment
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.databinding.WananzhuoFragmentNavigationBinding
import git.lxy.com.wananzhuoapp.ui.nav.adapter.NaviLeftAdapter
import git.lxy.com.wananzhuoapp.ui.nav.adapter.NaviRightAdapter
import git.lxy.com.wananzhuoapp.ui.nav.decoration.HeaderDecoration
import git.lxy.com.wananzhuoapp.ui.nav.entity.NavigationData
import git.lxy.com.wananzhuoapp.ui.nav.vm.NavigationViewModel
import git.lxy.com.wananzhuoapp.ui.web.WebActivity
import kotlinx.android.synthetic.main.wananzhuo_fragment_navigation.*

/**
 *  @author lxy
 *  @date 2018/8/23
 *  @Description
 */
class NavigationFragment : BaseFragment() {

    lateinit var mBinding: WananzhuoFragmentNavigationBinding
    lateinit var mViewModel: NavigationViewModel
    lateinit var mLeftList: ArrayList<String>
    lateinit var mRightList: ArrayList<NavigationData>
    lateinit var mLeftAdapter: NaviLeftAdapter
    lateinit var mRightAdapter: NaviRightAdapter
    lateinit var mRightLayoutManager: LinearLayoutManager
    lateinit var mLeftLayoutManager: LinearLayoutManager
    var mMove = false
    var mIsLeft = false
    var mClickIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.wananzhuo_fragment_navigation, container, false)

        return mBinding.root
    }

    override fun firstVisiableToUser() {
        mViewModel = ViewModelProviders.of(this).get(NavigationViewModel::class.java)
        mLeftList = arrayListOf()
        mRightList = arrayListOf()

        mLeftLayoutManager = LinearLayoutManager(context)
        leftRecyclerView.layoutManager = mLeftLayoutManager
        mLeftAdapter = NaviLeftAdapter(R.layout.wananzhuo_list_item_navi_left_list, mLeftList)
        leftRecyclerView.adapter = mLeftAdapter

        mLeftAdapter.run {

            setOnItemClickListener { adapter, view, position ->
                mClickIndex = position
                setLeftListChecked(position)
            }
        }

        mRightLayoutManager = LinearLayoutManager(context)
        rightRecyclerView.layoutManager = mRightLayoutManager
        mRightAdapter = NaviRightAdapter(R.layout.wananzhuo_list_item_navi_right_list, mRightList)
        rightRecyclerView.addItemDecoration(context?.let { HeaderDecoration(it, mRightList) })
        rightRecyclerView.adapter = mRightAdapter
        rightRecyclerView.addOnScrollListener(scrollListener)

        mRightAdapter.run {
            mutableLiveData.observe(this@NavigationFragment, Observer {
                it?.let {
                    WebActivity.openActivity(context, it)
                }
            })
        }

        loadData()
    }

    fun loadData() {
        mViewModel.getNaviList(this, true)
                ?.observe(this, Observer {
                    mLeftList.clear()
                    it?.forEach {
                        mLeftList.add(it.name)
                    }
                    mLeftAdapter.notifyDataSetChanged()

                    it?.let {
                        mRightList.clear()
                        mRightList.addAll(it)
                        mRightAdapter.notifyDataSetChanged()
                    }
                })
    }

    fun setLeftListChecked(position: Int) {

        mLeftAdapter.setCheckedPosition(position)
        smoothMoveToPosition(position)

        //
        moveToCenter(position)
    }

    fun moveToCenter(position: Int) {
        val childAt = leftRecyclerView.getChildAt(position - mLeftLayoutManager.findFirstVisibleItemPosition())
        childAt?.let {
            val y = it.getTop() - leftRecyclerView.getHeight() / 2
            leftRecyclerView.smoothScrollBy(0, y)
        }
    }

    /**
     * 点击左侧列表,矫正第三种情况
     * 监听右侧列表，滚动停止
     */
    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == RecyclerView.SCROLL_STATE_IDLE && mMove) {
                mMove = false
                var first = mRightLayoutManager.findFirstVisibleItemPosition()
                var n = mClickIndex - first
                if (n >= 0 && n < rightRecyclerView.childCount) {
                    var top = rightRecyclerView.getChildAt(n).top
                    rightRecyclerView.smoothScrollBy(0, top)
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }

    /**
     * 点击左侧列表，右侧联动
     */
    fun smoothMoveToPosition(i: Int) {
        var firstVisiablePosition = mRightLayoutManager.findFirstVisibleItemPosition()
        var lastPosition = mRightLayoutManager.findLastVisibleItemPosition()
        if (i <= firstVisiablePosition) {
            rightRecyclerView.smoothScrollToPosition(i)
        } else if (i <= lastPosition) {
            var top = rightRecyclerView.getChildAt(i - firstVisiablePosition).top
            rightRecyclerView.smoothScrollBy(0, top)
        } else {
            rightRecyclerView.smoothScrollToPosition(i)
            mMove = true
        }
    }
}
package git.lxy.com.wananzhuoapp.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.article.demos.common.base.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.databinding.WananzhuoFragmentHomeBinding
import git.lxy.com.wananzhuoapp.main.ArticleBean
import git.lxy.com.wananzhuoapp.main.BannerBean
import git.lxy.com.wananzhuoapp.ui.flutter.FlutterSearchActivity
import git.lxy.com.wananzhuoapp.ui.home.adapter.HomeBannerAdapter
import git.lxy.com.wananzhuoapp.ui.home.adapter.HomeFragmentAdapter
import git.lxy.com.wananzhuoapp.ui.home.vm.HomeViewModel
import git.lxy.com.wananzhuoapp.ui.tree.activity.TreeChildActivity
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeChild
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeEntity
import git.lxy.com.wananzhuoapp.ui.web.WebActivity
import kotlinx.android.synthetic.main.wananzhuo_fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *  @author lxy
 *  @Description
 */
class HomeFragment : BaseFragment(), BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    lateinit var mBinding: WananzhuoFragmentHomeBinding
    lateinit var mViewModel: HomeViewModel
    lateinit var list: ArrayList<ArticleBean>
    lateinit var mHomeAdapter: HomeFragmentAdapter
    lateinit var mBannerRecyclerView: RecyclerView
    lateinit var mBannerAdapter: HomeBannerAdapter
    lateinit var mBannerList: ArrayList<BannerBean>
    private var mBannerJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.wananzhuo_fragment_home, container, false)
        return mBinding.root
    }

    override fun firstVisiableToUser() {
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        lifecycle.addObserver(mViewModel)

        list = ArrayList()
        mHomeAdapter = HomeFragmentAdapter(R.layout.wananzhuo_home_fragment_list_item, list)

        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mHomeAdapter)
        }

        mHomeAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                WebActivity.openActivity(context, list[position].link)
            }
            setOnItemChildClickListener(this@HomeFragment)
            setOnLoadMoreListener(this@HomeFragment, recyclerView)
        }

        mBannerRecyclerView = layoutInflater.inflate(R.layout.wananzhuo_home_banner, null, false) as RecyclerView
        mBannerList = arrayListOf()
        mBannerAdapter = HomeBannerAdapter(R.layout.wananzhuo_banner_item, mBannerList)

        PagerSnapHelper().run {
            attachToRecyclerView(mBannerRecyclerView)
        }

        mBannerRecyclerView.run {
            val layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            setLayoutManager(layoutManager)
            adapter = mBannerAdapter
        }

        mBannerAdapter.run {
            bindToRecyclerView(mBannerRecyclerView)
            setOnItemClickListener { adapter, view, position ->
                var item: BannerBean = adapter.getItem(position) as BannerBean
                WebActivity.openActivity(context, item.url)
            }
        }

        mHomeAdapter.addHeaderView(mBannerRecyclerView)

        swipeRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener(this@HomeFragment)
        }
        flab.setOnClickListener {
            //            recyclerView.smoothScrollToPosition(0)
            FlutterSearchActivity.openActivity(context, "search")
        }

        mViewModel.refreshLiveData.observe(this, Observer {
            onRefresh()
        })
        onRefresh()
    }

    /**
     * 在后台启动新的协程并继续
     */
    private fun getBannerJob() = GlobalScope.launch {
        repeat(Int.MAX_VALUE) {
            if (mBannerList.size == 0) {
                return@launch
            }
            delay(3000L)
            val position = it % mBannerList.size
            mBannerRecyclerView.smoothScrollToPosition(position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBannerJob?.run {
            cancel()
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (list.size == 0) {
            return
        }
        var bean = list[position]
        when (view?.id) {
            R.id.homeItemType -> {
                var treeChild = TreeChild(bean.tags, 0, bean.chapterId, bean.chapterName, 0, 0, 0)
                var treeEntity = TreeEntity(listOf(treeChild), bean.courseId, bean.id, bean.chapterName, 0, bean.superChapterId, bean.visible)

                TreeChildActivity.openActivity(context, treeEntity)
            }
            R.id.homeItemLike -> {

                if (bean.collect) {
                    unCollectArticle(bean, position)
                } else {
                    collectArticle(bean, position)
                }
            }
        }
    }

    /**
     * 收藏
     */
    fun collectArticle(bean: ArticleBean, position: Int) {

        mViewModel.clickLike(bean.id, this)
                .observe(this, Observer {
                    bean.collect = true
                    mHomeAdapter.setData(position, bean)
                    mHomeAdapter.notifyDataSetChanged()
                    showToast("收藏成功")
                })
    }

    /**
     * 取消收藏
     */
    fun unCollectArticle(bean: ArticleBean, position: Int) {
        mViewModel.clickUnLike(bean.id, this)
                .observe(this, Observer {
                    bean.collect = false
                    mHomeAdapter.setData(position, bean)
                    mHomeAdapter.notifyDataSetChanged()
                    showToast("取消收藏成功")
                })
    }

    /**
     * loadmore
     */
    override fun onLoadMoreRequested() {
        var page = mHomeAdapter.data.size / 20 + 1
        loadData(page, false, false)
    }

    /**
     * refresh
     */
    override fun onRefresh() {
        mHomeAdapter.setEnableLoadMore(false)
        loadData(0, false, true)
        loadBanner()
    }

    fun loadData(page: Int, showLoading: Boolean, isRefresh: Boolean) {

        mViewModel.loadData(this, page, showLoading)
                ?.observe(this, Observer {

                    it?.run {
                        if (isRefresh) {
                            mHomeAdapter.setEnableLoadMore(true)
                            swipeRefreshLayout.isRefreshing = false
                        }
                        setData(isRefresh, this.datas)
                    } ?: let {
                        mHomeAdapter.setEnableLoadMore(true)
                        swipeRefreshLayout.isRefreshing = false
                        if (!isRefresh) {
                            mHomeAdapter.loadMoreFail()
                        }
                    }
                })
    }

    fun loadBanner() {
        mViewModel.getBanner(this, false)
                ?.observe(this, Observer {
                    it ?: return@Observer
                    mBannerList.clear()
                    mBannerList.addAll(it)
                    mBannerAdapter.notifyDataSetChanged()

                    mBannerJob?.run {

                    } ?: let {
                        mBannerJob = getBannerJob()
                    }
                })
    }

    fun setData(isRefresh: Boolean, list: List<ArticleBean>) {
        if (isRefresh) {
            this.list.clear()
        }
        this.list.addAll(list)
        mHomeAdapter.notifyDataSetChanged()

        if (list.size < 20) {
            mHomeAdapter.loadMoreEnd(isRefresh)
        } else {
            mHomeAdapter.loadMoreComplete()
        }
    }

}
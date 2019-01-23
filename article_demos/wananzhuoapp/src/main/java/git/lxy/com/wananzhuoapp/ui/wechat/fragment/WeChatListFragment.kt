package git.lxy.com.wananzhuoapp.ui.wechat.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.article.demos.common.base.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.main.ArticleBean
import git.lxy.com.wananzhuoapp.ui.home.vm.HomeViewModel
import git.lxy.com.wananzhuoapp.ui.web.WebActivity
import git.lxy.com.wananzhuoapp.ui.wechat.adapter.HistoryAdapter
import git.lxy.com.wananzhuoapp.ui.wechat.bean.WeChatArticleData
import git.lxy.com.wananzhuoapp.ui.wechat.vm.WeChatArticleViewModel
import kotlinx.android.synthetic.main.wananzhuo_fragment_wechat_list.*

/**
 *  @author lxy
 *  @date 2018/10/16
 *  @Description 公众号历史文章页面
 */
class WeChatListFragment : BaseFragment(), BaseQuickAdapter.OnItemChildClickListener {

    lateinit var mViewModel: WeChatArticleViewModel
    lateinit var mHomeViewModel: HomeViewModel
    lateinit var mAdapter: HistoryAdapter
    lateinit var mArticleList: ArrayList<WeChatArticleData>
    var weChatId = 0

    companion object {
        val WE_CHAT_ID = "we_chat_id"

        fun newInstance(id: Int): Fragment {
            var fragment = WeChatListFragment()
            var bundle = Bundle()
            bundle.putInt(WE_CHAT_ID, id)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wananzhuo_fragment_wechat_list, container, false)
    }

    override fun firstVisiableToUser() {
        // do nothing
    }

    override fun firstVisiableToUserVp() {
        super.firstVisiableToUserVp()
        mViewModel = ViewModelProviders.of(this).get(WeChatArticleViewModel::class.java)
        lifecycle.addObserver(mViewModel)
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mArticleList = arrayListOf()
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189))
        swipeRefreshLayout.setOnRefreshListener(refreshListener)

        mAdapter = HistoryAdapter(R.layout.wananzhuo_list_item_wechat_history, mArticleList)
        historyRecyclerView.layoutManager = LinearLayoutManager(context)
        historyRecyclerView.adapter = mAdapter

        mAdapter.let {
            it.setOnItemClickListener { adapter, view, position -> WebActivity.openActivity(context, mArticleList[position].link) }
            it.setOnLoadMoreListener(loadMoreListener, historyRecyclerView)
            it.setOnItemChildClickListener(this@WeChatListFragment)
        }

        arguments?.let {
            weChatId = it.getInt(WE_CHAT_ID)
            swipeRefreshLayout.isRefreshing = true
            loadData(true, weChatId, 1, true)
        }

        mViewModel.refreshLiveData
                .observe(this, Observer {
                    refresh()
                })
    }

    fun loadData(showLoading: Boolean, id: Int, page: Int, isRefresh: Boolean) {
        mViewModel.getWeChatArticleHistory(showLoading, this, id, page)
                ?.observe(this, Observer {

                    it?.run {
                        if (isRefresh) {
                            mAdapter.setEnableLoadMore(true)
                            swipeRefreshLayout.isRefreshing = false
                        }
                        setData(isRefresh, it.datas)
                    } ?: let {
                        mAdapter.setEnableLoadMore(true)
                        swipeRefreshLayout.isRefreshing = false
                        if (!isRefresh) {
                            mAdapter.loadMoreFail()
                        }
                    }
                })
    }

    fun loadMore() {
        var page = mAdapter.data.size / 20 + 1 // 每次请求返回20条数据
        loadData(false, weChatId, page, false)
    }

    fun refresh() {
        mAdapter.setEnableLoadMore(false)
        loadData(false, weChatId, 1, true)
    }

    fun setData(isRefresh: Boolean, list: List<WeChatArticleData>) {
        if (isRefresh) {
            mArticleList.clear()
        }
        mArticleList.addAll(list)
        mAdapter.notifyDataSetChanged()

        if (list.size < 20) {
            mAdapter.loadMoreEnd(isRefresh)
        } else {
            mAdapter.loadMoreComplete()
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (mArticleList.size == 0) {
            return
        }
        var bean = mArticleList[position]
        when (view?.id) {
            R.id.ivLike -> {
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
    fun collectArticle(bean: WeChatArticleData, position: Int) {

        mHomeViewModel.clickLike(bean.id, this)
                .observe(this, Observer {
                    bean.collect = true
                    mAdapter.setData(position, bean)
                    mAdapter.notifyDataSetChanged()
                    showToast("收藏成功")
                })
    }

    /**
     * 取消收藏
     */
    fun unCollectArticle(bean: WeChatArticleData, position: Int) {
        mHomeViewModel.clickUnLike(bean.id, this)
                .observe(this, Observer {
                    bean.collect = false
                    mAdapter.setData(position, bean)
                    mAdapter.notifyDataSetChanged()
                    showToast("取消收藏成功")
                })
    }

    private var loadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        loadMore()
    }

    private var refreshListener = SwipeRefreshLayout.OnRefreshListener {
        refresh()
    }
}
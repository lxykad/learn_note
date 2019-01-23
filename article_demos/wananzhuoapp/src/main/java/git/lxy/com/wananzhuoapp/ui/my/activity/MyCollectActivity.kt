package git.lxy.com.wananzhuoapp.ui.my.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.article.demos.common.base.BaseActivity
import com.article.demos.common.ext.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.ui.my.adapter.CollectListAdapter
import git.lxy.com.wananzhuoapp.ui.my.entity.CollectArticle
import git.lxy.com.wananzhuoapp.ui.my.event.RefreshCollectEvent
import git.lxy.com.wananzhuoapp.ui.my.vm.CollectViewModel
import git.lxy.com.wananzhuoapp.ui.web.WebActivity
import kotlinx.android.synthetic.main.wananzhuo_activity_my_collect.*
import org.greenrobot.eventbus.EventBus

/**
 *  @author lxy
 *  @date 2018/10/18
 *  @Description
 */
class MyCollectActivity : BaseActivity(), BaseQuickAdapter.OnItemChildClickListener {

    lateinit var mViewModel: CollectViewModel
    lateinit var mAdapter: CollectListAdapter
    lateinit var mArticleList: ArrayList<CollectArticle>
    lateinit var mEmptyView: View

    companion object {

        fun openActivity(context: Context?) {

            Intent(context, MyCollectActivity::class.java).run {
                context?.startActivity(this)
            }
        }
    }

    override fun getLayoutId() = R.layout.wananzhuo_activity_my_collect

    override fun init() {
        mViewModel = ViewModelProviders.of(this).get(CollectViewModel::class.java)
        lifecycle.addObserver(mViewModel)
        mEmptyView = layoutInflater.inflate(R.layout.wananzhuo_empty_view, null)

        mArticleList = arrayListOf()
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189))
        swipeRefreshLayout.setOnRefreshListener(refreshListener)

        mAdapter = CollectListAdapter(R.layout.wananzhuo_list_item_my_collect, mArticleList)
        collectRecyclerView.layoutManager = LinearLayoutManager(this)
        collectRecyclerView.adapter = mAdapter

        mAdapter.let {
            it.setOnItemChildClickListener(this@MyCollectActivity)
            it.setOnItemClickListener { adapter, view, position -> WebActivity.openActivity(this, mArticleList[position].link) }
            it.setOnLoadMoreListener(loadMoreListener, collectRecyclerView)
        }

        refresh()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (mArticleList.size == 0) {
            return
        }
        var bean = mArticleList[position]
        when (view?.id) {
            R.id.ivLike -> {
                cancelCollect(bean, position)
            }
        }
    }

    fun cancelCollect(article: CollectArticle, position: Int) {
        mViewModel.unCollect(article.id, article.originId, this)
                ?.observe(this, Observer {
                    mAdapter.remove(position)
                    toast("取消收藏成功")
                    if (mAdapter.data.size == 0) {
                        mAdapter.emptyView = mEmptyView
                    }
                })
    }

    fun loadData(page: Int, isRefresh: Boolean) {

        mViewModel.getCollect(this, page)
                ?.observe(this, Observer {
                    if (it == null) {
                        mAdapter.setEnableLoadMore(true)
                        swipeRefreshLayout.isRefreshing = false
                        if (!isRefresh) {
                            mAdapter.loadMoreFail()
                        }
                    } else {
                        if (isRefresh) {
                            mAdapter.setEnableLoadMore(true)
                            swipeRefreshLayout.isRefreshing = false
                        }
                        setData(isRefresh, it.datas)
                    }
                })
    }

    fun setData(isRefresh: Boolean, list: List<CollectArticle>) {
        if (isRefresh && list.size == 0) {
            mAdapter.emptyView = mEmptyView
            return
        }
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

    fun loadMore() {
        var page = mAdapter.data.size / 20 + 1 // 每次请求返回20条数据
        loadData(page, false)
    }

    fun refresh() {
        mAdapter.setEnableLoadMore(false)
        swipeRefreshLayout.isRefreshing = true
        loadData(0, true)
    }

    private var loadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        loadMore()
    }

    private var refreshListener = SwipeRefreshLayout.OnRefreshListener {
        refresh()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        EventBus.getDefault().post(RefreshCollectEvent(RefreshCollectEvent.TYPE_REFRESH))
    }
}
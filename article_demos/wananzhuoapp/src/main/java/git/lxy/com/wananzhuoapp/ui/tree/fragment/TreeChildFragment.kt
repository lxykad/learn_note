package git.lxy.com.wananzhuoapp.ui.tree.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.article.demos.common.base.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.ui.home.vm.HomeViewModel
import git.lxy.com.wananzhuoapp.ui.tree.adapter.TreeChildFragmentAdapter
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeChildArticle
import git.lxy.com.wananzhuoapp.ui.tree.vm.TreeChildArticleViewModel
import git.lxy.com.wananzhuoapp.ui.web.WebActivity
import kotlinx.android.synthetic.main.wananzhuo_fragment_tree_child.*

/**
 *  @author lxy
 *  @date 2018/9/5
 *  @Description
 */
class TreeChildFragment : BaseFragment() {

    var mTypeId = 0
    lateinit var mViewModel: TreeChildArticleViewModel
    lateinit var mHomeViewModel: HomeViewModel
    lateinit var mAdapter: TreeChildFragmentAdapter
    lateinit var mList: ArrayList<TreeChildArticle>

    companion object {
        private var ARTICLE_ID = "article_id"

        fun newInstance(id: Int): TreeChildFragment {

            var fragment = TreeChildFragment()
            var bundle = Bundle()
            bundle.putInt(ARTICLE_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wananzhuo_fragment_tree_child, container, false)
    }

    override fun firstVisiableToUser() {
        // do nothing
    }

    override fun firstVisiableToUserVp() {
        mTypeId = arguments?.get(ARTICLE_ID) as Int
        mViewModel = ViewModelProviders.of(this).get(TreeChildArticleViewModel::class.java)
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        mList = ArrayList()
        mAdapter = TreeChildFragmentAdapter(R.layout.wananzhuo_list_item_tree_child, mList)

        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        mAdapter.run {
            setOnItemClickListener { adapter, view, position -> WebActivity.openActivity(context, mList[position].link) }
            onItemChildClickListener = this@TreeChildFragment.onItemChildClickListener
        }
        loadData()
    }

    fun loadData() {
        mViewModel.getArticleList(mTypeId, 0, true, this)
                ?.observe(this, Observer {
                    it?.let {
                        mList.addAll(it.datas)
                        mAdapter.notifyDataSetChanged()
                    }
                })
    }

    private var onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                if (mList.size != 0) {
                    var bean = mList[position]
                    when (view?.id) {
                        R.id.homeItemLike ->
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
    fun collectArticle(bean: TreeChildArticle, position: Int) {

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
    fun unCollectArticle(bean: TreeChildArticle, position: Int) {
        mHomeViewModel.clickUnLike(bean.id, this)
                .observe(this, Observer {
                    bean.collect = false
                    mAdapter.setData(position, bean)
                    mAdapter.notifyDataSetChanged()
                    showToast("取消收藏成功")
                })
    }
}
package git.lxy.com.wananzhuoapp.ui.tree.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.article.demos.common.base.BaseFragment
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.databinding.WananzhuoFragmentKnowledgeBinding
import git.lxy.com.wananzhuoapp.ui.tree.activity.TreeChildActivity
import git.lxy.com.wananzhuoapp.ui.tree.adapter.TreeFragmentAdapter
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeEntity
import git.lxy.com.wananzhuoapp.ui.tree.vm.KnowledgeFragmentViewModel
import kotlinx.android.synthetic.main.wananzhuo_fragment_knowledge.*

/**
 *  @author lxy
 *  @date 2018/8/23
 *  @Description
 */
class KnowledgeFragment : BaseFragment() {

    lateinit var mBinding: WananzhuoFragmentKnowledgeBinding
    lateinit var mViewModel: KnowledgeFragmentViewModel
    lateinit var mFragmentAdapter: TreeFragmentAdapter
    lateinit var mList: MutableList<TreeEntity>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.wananzhuo_fragment_knowledge, container, false)

        return mBinding.root
    }

    override fun firstVisiableToUser() {

        mViewModel = ViewModelProviders.of(this).get(KnowledgeFragmentViewModel::class.java)
        mList = ArrayList()
        mFragmentAdapter = TreeFragmentAdapter(R.layout.wananzhuo_tree_list_item, mList)

        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mFragmentAdapter
        }

        mFragmentAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val bean = mList[position]
                TreeChildActivity.openActivity(context, bean)
            }
        }

        loadData()
    }

    fun loadData() {

        mViewModel.getTreeData(this)
                ?.observe(this, Observer {
                    it?.let {
                        mList.addAll(it)
                        mFragmentAdapter.notifyDataSetChanged()
                    }
                })
    }
}
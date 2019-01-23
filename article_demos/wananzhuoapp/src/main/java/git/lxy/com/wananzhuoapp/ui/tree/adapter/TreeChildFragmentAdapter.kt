package git.lxy.com.wananzhuoapp.ui.tree.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeChildArticle

/**
 *  @author lxy
 *  @date 2018/9/6
 *  @Description
 */
class TreeChildFragmentAdapter(id: Int, list: List<TreeChildArticle>) :
        BaseQuickAdapter<TreeChildArticle, BaseViewHolder>(id, list) {

    override fun convert(holder: BaseViewHolder, item: TreeChildArticle?) {
        item ?:return

        holder.setText(R.id.homeItemTitle, item.title)
                .setText(R.id.homeItemAuthor, item.author)
                .setVisible(R.id.homeItemType, false)
                .setText(R.id.homeItemDate, item.niceDate)
                .setImageResource(
                        R.id.homeItemLike,
                        if (item.collect) R.drawable.wanandroid_ic_action_like else R.drawable.wanandroid_ic_action_no_like
                )
                .addOnClickListener(R.id.homeItemLike)
    }

}
package git.lxy.com.wananzhuoapp.ui.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.main.ArticleBean

/**
 * Created by lxy on 2018/8/26.
 */
class HomeFragmentAdapter(id: Int, list: List<ArticleBean>) :
        BaseQuickAdapter<ArticleBean, BaseViewHolder>(id, list) {
    override fun convert(holder: BaseViewHolder, bean: ArticleBean?) {
        bean ?: return
        holder.setText(R.id.homeItemTitle, bean.title)
                .setText(R.id.homeItemAuthor, bean.author)
                .setText(R.id.homeItemType, bean.chapterName)
                .setTextColor(R.id.homeItemType, holder.convertView.context.resources.getColor(R.color.colorPrimary))
                .setText(R.id.homeItemDate, bean.niceDate)
                .addOnClickListener(R.id.homeItemType)
                .linkify(R.id.homeItemType)
                .setImageResource(
                        R.id.homeItemLike,
                        if (bean.collect) R.drawable.wanandroid_ic_action_like else R.drawable.wanandroid_ic_action_no_like
                )
                .addOnClickListener(R.id.homeItemLike)
    }
}
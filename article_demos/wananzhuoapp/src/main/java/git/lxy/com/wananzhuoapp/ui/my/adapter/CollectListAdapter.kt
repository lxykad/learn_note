package git.lxy.com.wananzhuoapp.ui.my.adapter

import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.ui.my.entity.CollectArticle
import java.text.SimpleDateFormat

class CollectListAdapter(layoutId: Int, list: List<CollectArticle>)
    : BaseQuickAdapter<CollectArticle, BaseViewHolder>(layoutId, list) {

    override fun convert(holder: BaseViewHolder?, item: CollectArticle?) {
        holder ?: return
        item ?: return

        holder.setText(R.id.articleTitle, item.title)
                .setText(R.id.articleTime, TimeUtils.millis2String(item.publishTime, SimpleDateFormat("yyyy.MM.dd")))
                .setImageResource(R.id.ivLike, R.drawable.wanandroid_ic_action_like)
                .addOnClickListener(R.id.ivLike)
    }

}
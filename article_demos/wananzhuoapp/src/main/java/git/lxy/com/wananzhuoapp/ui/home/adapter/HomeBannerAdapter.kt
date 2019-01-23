package git.lxy.com.wananzhuoapp.ui.home.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.ext.loadUrl
import git.lxy.com.wananzhuoapp.main.BannerBean

class HomeBannerAdapter(id: Int, list: List<BannerBean>)
    : BaseQuickAdapter<BannerBean, BaseViewHolder>(id, list) {

    override fun convert(holder: BaseViewHolder?, bean: BannerBean?) {
        bean ?: return
        holder ?: return
        holder.setText(R.id.bannerTitle, bean.title)
                .setText(R.id.tvBannerCount, "${holder.adapterPosition + 1}/${mData.size}")
        var imageView = holder.getView<ImageView>(R.id.bannerImage)
        imageView.loadUrl(bean.imagePath)
    }
}
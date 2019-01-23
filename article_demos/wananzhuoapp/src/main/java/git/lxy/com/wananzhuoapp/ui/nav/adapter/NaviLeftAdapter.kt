package git.lxy.com.wananzhuoapp.ui.nav.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import git.lxy.com.wananzhuoapp.R

/**
 *  @author liuxinyu
 *  @date 2018/9/21
 *  @Description
 */
class NaviLeftAdapter(id: Int, list: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(id, list) {

    private var checkedPosition: Int = 0

    fun setCheckedPosition(position: Int) {
        this.checkedPosition = position
        notifyDataSetChanged()
    }

    override fun convert(holder: BaseViewHolder?, item: String?) {
        holder ?: return
        item?.let {
            holder.setText(R.id.tvName, item)
        }
        val position: Int = holder.adapterPosition
        if (position == checkedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#dddddd"))
        }
    }
}
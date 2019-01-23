package git.lxy.com.wananzhuoapp.ui.nav.adapter

import android.arch.lifecycle.MutableLiveData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.ui.nav.entity.NavigationData


/**
 *  @author liuxinyu
 *  @date 2018/9/21
 *  @Description
 */
class NaviRightAdapter(id: Int, list: List<NavigationData>) : BaseQuickAdapter<NavigationData, BaseViewHolder>(id, list) {

    var mutableLiveData = MutableLiveData<String>()

    override fun convert(holder: BaseViewHolder?, bean: NavigationData?) {
        holder ?: return
        bean?.let {
            holder.setText(R.id.tvName, bean.name)
        }
        val flex: FlexboxLayout = holder.getView(R.id.flexLayout)
        val flexParams = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        flexParams.leftMargin = 20
        flexParams.topMargin = 20

        bean?.let {
            it.articles.forEach {
                val view = LayoutInflater.from(flex.getContext()).inflate(R.layout.wananzhuo_item_navi_tag, null)
                val tvName: TextView = view.findViewById(R.id.tvName)
                val link = it.link
                tvName.setText(it.title)
                tvName.layoutParams = flexParams
                tvName.setOnClickListener {
                    mutableLiveData.value = link
                }
                flex.addView(tvName)
            }
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        val flex: FlexboxLayout = holder.getView(R.id.flexLayout)
        flex.removeAllViews()
    }
}
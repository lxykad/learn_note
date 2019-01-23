package git.lxy.com.wananzhuoapp.ui.tree.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import git.lxy.com.wananzhuoapp.R
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeEntity

class TreeFragmentAdapter(resId: Int, list: MutableList<TreeEntity>) :
        BaseQuickAdapter<TreeEntity, BaseViewHolder>(resId, list) {

    override fun convert(holder: BaseViewHolder?, item: TreeEntity?) {
        item ?: return
        holder?.setText(R.id.typeItemFirst, item.name)
        item.children?.let {
            holder?.setText(R.id.typeItemSecond,
                    it.joinToString("    ") {
                        it.name
                    }
            )
        }
    }
}
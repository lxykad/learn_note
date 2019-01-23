package git.lxy.com.wananzhuoapp.ui.nav.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import git.lxy.com.wananzhuoapp.ui.nav.entity.NavigationData

/**
 *  @author lxy
 *  @date 2018/10/12
 *  @Description
 */
class HeaderDecoration(context: Context, list: List<NavigationData>) : RecyclerView.ItemDecoration() {


    init {
        var paint = Paint()
        paint.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
       // super.onDrawOver(c, parent, state)

    }
}
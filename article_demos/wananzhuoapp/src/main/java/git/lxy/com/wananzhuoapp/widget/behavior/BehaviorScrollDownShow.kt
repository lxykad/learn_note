package git.lxy.com.wananzhuoapp.widget.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View

/**
 *  @author lxy
 *  @date 2018/9/3
 *  @Description 自定义behavior 向下滑动隐藏 上滑显示
 */
class BehaviorScrollDownShow : FloatingActionButton.Behavior {

    private var isAnimate = false
    private var isShow = true
    private var FAST_OUT_SLOW_IN_INTERPOLATOR = LinearOutSlowInInterpolator()

    constructor(context: Context, attributeSet: AttributeSet) {

    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        //  super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        if (dyConsumed > 0 || dyUnconsumed > 0 && !isAnimate && isShow) {

            child.setVisibility(View.VISIBLE)
            isShow = false
            ViewCompat.animate(child)
                    .translationY(350f)
                    .setDuration(400)
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .setListener(AnimatorListener())
                    .start()

        } else if (dyConsumed < 0 || dyUnconsumed < 0 && !isAnimate && !isShow) {
            // 手指下滑，显示FAB
            child.setVisibility(View.VISIBLE)
            isShow = true
            ViewCompat.animate(child)
                    .translationY(0f)
                    .setDuration(400)
                    .setListener(AnimatorListener())
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .start()
        }
    }

    override fun onNestedFling(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    inner class AnimatorListener : ViewPropertyAnimatorListener {

        override fun onAnimationEnd(view: View?) {
            isAnimate = false
        }

        override fun onAnimationCancel(view: View?) {
            isAnimate = false
        }

        override fun onAnimationStart(view: View?) {
            isAnimate = true
        }

    }
}
package com.article.demos.common.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.article.demos.common.R


abstract class BaseDialogFragment : DialogFragment(), View.OnClickListener {

    private var clickSpaceDismiss = true
    protected var rootView: View? = null
    @StyleRes
    private var styleResId = R.style.Translucent_NoTitle
    @StyleRes
    private var windowAnimationResId = 0

    @get:LayoutRes
    protected abstract val contentView: Int

    fun setStyleResId(@StyleRes styleResId: Int) {
        this.styleResId = styleResId
    }

    fun setWindowAnimationResId(@StyleRes animationResId: Int) {
        this.windowAnimationResId = animationResId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, styleResId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentViewResId = contentView
        rootView = inflater.inflate(contentViewResId, container)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (clickSpaceDismiss) {
            rootView!!.setOnClickListener(this)
        }
        init(rootView)
    }

    fun setClickSpaceDismiss(clickSpaceDismiss: Boolean) {
        this.clickSpaceDismiss = clickSpaceDismiss
        if (rootView != null) {
            rootView!!.setOnClickListener(if (clickSpaceDismiss) this else null)
        }
    }

    override fun onStart() {
        val window = dialog.window
        if (window != null) {
            if (windowAnimationResId != 0) {
                window.setWindowAnimations(windowAnimationResId)
            }
            window.decorView.setPadding(0, 0, 0, 0)
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        super.onStart()
    }

    override fun onClick(v: View) {
        if (v === rootView && clickSpaceDismiss) {
            dismiss()
        }
    }

    protected abstract fun init(view: View?)
}

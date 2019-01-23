package com.article.demos.common.widgets

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import com.article.demos.common.R
import com.article.demos.common.base.BaseDialogFragment

import kotlinx.android.synthetic.main.common_loading_layout.*

/**
 *  @author lxy
 *  @Description
 */
class LoadingDialog : BaseDialogFragment() {


    lateinit var msg: String

    override val contentView: Int = R.layout.common_loading_layout

    override fun init(view: View?) {

    }

    fun setMessage(msg: String) {
        this.msg = msg
        loading_text?.text = msg ?: ""
    }

    fun isShowing(): Boolean {
        return isAdded && isVisible
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading_text.text = msg ?: ""
    }

    companion object {
        var TAG = "loadingDialog"

        fun showLoading(manager: FragmentManager,
                        cancelable: Boolean,
                        canceledOnTouchOutside: Boolean): LoadingDialog {
            val dialog = LoadingDialog()
            dialog.isCancelable = cancelable
            dialog.setClickSpaceDismiss(canceledOnTouchOutside)
            val fragmentTransaction = manager.beginTransaction()
            fragmentTransaction.add(dialog, TAG)
            fragmentTransaction.commitAllowingStateLoss()
            return dialog
        }
    }

}
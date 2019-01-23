package com.article.demos.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.article.demos.common.view.BaseView;
import com.article.demos.common.widgets.LoadingDialog;

import org.jetbrains.annotations.NotNull;

/**
 * @author lxy
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    public String TAG = "BaseFragment";
    /**
     * 标识fragment视图已经初始化完毕
     */
    private boolean mIsViewPrepared;

    /**
     * 标识已经触发过懒加载数据
     */
    private boolean mHasFetchData;

    /**
     * 标识fragment视图已经初始化完毕
     */
    private boolean isPreparedWithVp = false;
    /**
     * 第一次对用户可见
     */
    private boolean isFirstUserVisibleVp = true;
    /**
     * 第一次对用户不可见
     */
    private boolean isFirstUserInVisibleVp = true;

    private LoadingDialog dialog;
    private Toast mToast;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            lazyFetchDataIfPrepared();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsViewPrepared = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHasFetchData = false;
        mIsViewPrepared = false;
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }

        if (isVisibleToUser) {
            if (isFirstUserVisibleVp) {
                initPrepare();
            } else {
                visiableToUserVp();
            }
        } else {
            if (isFirstUserInVisibleVp) {
                isFirstUserInVisibleVp = false;
                firstInVisiableToUserVp();
            } else {
                inVisiableToUserVp();
            }
        }
    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !mHasFetchData && mIsViewPrepared) {
            mHasFetchData = true; //已加载过数据
            firstVisiableToUser();
        }
        //
        if (getUserVisibleHint() && mIsViewPrepared) {

            visiableToUser();
        }
    }

    protected void initPrepare() {
        if (isPreparedWithVp) {
            isFirstUserVisibleVp = false;
            firstVisiableToUserVp();
        } else {
            isPreparedWithVp = true;
        }
    }

    /**
     * 懒加载 第一次对用户可见
     * 用于fragment的show和hide方法展示和隐藏
     */
    protected abstract void firstVisiableToUser();

    /**
     * 对用户可见 根据需要复写
     * 用于fragment的show和hide方法展示和隐藏
     */
    public void visiableToUser() {

    }

    /**
     * 懒加载 第一次对用户可见
     * 配合viewpager使用
     */
    protected void firstVisiableToUserVp() {

    }

    /**
     * 对用户可见 根据需要复写
     * 配合viewpager使用
     */
    public void visiableToUserVp() {
        //do nothing
    }

    /**
     * 配合viewpager使用
     */
    public void firstInVisiableToUserVp() {
        // do nothing
    }

    /**
     * 对用户不可见 根据需要复写
     * 配合viewpager使用
     */
    public void inVisiableToUserVp() {
        // do nothing
    }

    public void showLoading(String msg, boolean cancelable, boolean canceledOnTouchOutside) {
        if (!isAdded()) {
            return;
        }
        if (dialog == null) {
            dialog = LoadingDialog.Companion.showLoading(getActivity().getSupportFragmentManager(),
                    cancelable, canceledOnTouchOutside);
            dialog.setMessage(msg);
        } else {
            dialog.setClickSpaceDismiss(canceledOnTouchOutside);
            dialog.setCancelable(cancelable);
            dialog.setMessage(msg);
        }
    }

    public void hideLoading() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    @Override
    public void showLoading() {
        showLoading("", true, true);
    }

    @Override
    public void dismissLoading() {
        hideLoading();
    }

    @Override
    public void showError(@NotNull String msg) {
        showToast(msg);
    }
}

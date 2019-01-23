package com.article.demos.android.ui;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.article.demos.android.R;
import com.article.demos.android.databinding.FragmentAndroidBinding;
import com.article.demos.common.base.BaseFragment;
import com.article.demos.common.base.IService;
import com.article.demos.common.constant.Constant;
import com.article.demos.common.event.TestEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author lxy
 */
@Route(path = Constant.ANDROID_PAHT)
public class AndroidFragment extends BaseFragment {

    private FragmentAndroidBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_android, container, false);

        return mBinding.getRoot();
    }

    @Override
    protected void firstVisiableToUser() {

        /**
         * 推荐使用方式二来获取IService
         */
        // IService iService = (IService) ARouter.getInstance().build(Constant.WX_LOGIN).navigation();
        IService iService = ARouter.getInstance().navigation(IService.class);

        mBinding.bt.setOnClickListener(v -> ARouter.getInstance().build(Constant.WAN_ANDROID_MAIN).navigation());

        mBinding.btWx.setOnClickListener(v -> {
            EventBus.getDefault().post(new TestEvent());
        });

        mBinding.btLogin.setOnClickListener(v -> {
            String s = iService.wxLogin();
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        });

        mBinding.btRx.setOnClickListener(v -> ARouter.getInstance().build(Constant.RX_ACTIVITY).navigation());

    }

}

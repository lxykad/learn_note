package com.article.demos.java.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.article.demos.common.base.BaseFragment;
import com.article.demos.common.constant.Constant;
import com.article.demos.common.event.TestEvent;
import com.article.demos.java.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * @author a
 */
@Route(path = Constant.JAVA_PAHT)
public class JavaFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(JavaFragment.this);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_java, container, false);

        return binding.getRoot();
    }

    @Override
    protected void firstVisiableToUser() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(JavaFragment.this);
    }

    @Subscribe
    public void oNTestEvent(TestEvent event) {
        Toast.makeText(getContext(),"event",Toast.LENGTH_SHORT).show();
    }
}

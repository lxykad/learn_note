package com.article.demos.vue.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.article.demos.common.base.BaseActivity;
import com.article.demos.common.constant.Constant;
import com.article.demos.vue.R;

/**
 * @author a
 */
@Route(path = Constant.VUE_ACTIVITY)
public class VueActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vue;
    }

    @Override
    protected void init() {

    }

    public String getString() {

        return "vue";
    }
}

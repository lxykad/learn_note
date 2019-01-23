package com.article.demos.java.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.article.demos.common.base.BaseActivity;
import com.article.demos.common.constant.Constant;
import com.article.demos.java.R;


/**
 * @author a
 */
@Route(path = Constant.JAVA_ACTIVITY)
public class JavaActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_java;
    }

    @Override
    protected void init() {

    }

}

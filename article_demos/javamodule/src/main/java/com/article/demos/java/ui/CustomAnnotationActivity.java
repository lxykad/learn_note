package com.article.demos.java.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.article.demos.common.constant.Constant;
import com.article.demos.java.R;

/**
 * @author a
 */
@Route(path = Constant.JAVA_ANNOTATION_ACTIVITY)
public class CustomAnnotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_annotation);
    }
}

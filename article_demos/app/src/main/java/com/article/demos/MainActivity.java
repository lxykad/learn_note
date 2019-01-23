package com.article.demos;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.article.demos.android.ui.AndroidFragment;
import com.article.demos.common.constant.Constant;
import com.article.demos.databinding.ActivityMainBinding;
import com.article.demos.java.ui.JavaFragment;
import com.article.demos.kotlin.ui.KotlinFragment;
import com.article.demos.vue.ui.VueFragment;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

/**
 * @author a
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private ArrayList<Fragment> mList = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabList = new ArrayList<>();

    private String[] mTitles = {"Android", "Java", "Kotlin", "Vue"};
    private int[] mIconSelectIds = {
            R.drawable.ic_android_selected, R.drawable.ic_java_sesected,
            R.drawable.ic_kotlin_selected, R.drawable.ic_vue_selected};
    private int[] mIconUnselectIds = {
            R.drawable.ic_android_unselected, R.drawable.ic_java_unselected,
            R.drawable.ic_kotlin_unselected, R.drawable.ic_vue_unselected};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initData();
    }

    private void initData() {
        JavaFragment java = (JavaFragment) ARouter.getInstance().build(Constant.JAVA_PAHT).navigation();
        AndroidFragment android = (AndroidFragment) ARouter.getInstance().build(Constant.ANDROID_PAHT).navigation();
        KotlinFragment kotlin = (KotlinFragment) ARouter.getInstance().build(Constant.KOTLIN_PAHT).navigation();
        VueFragment vue = (VueFragment) ARouter.getInstance().build(Constant.VUE_PAHT).navigation();

        mList.add(android);
        mList.add(java);
        mList.add(kotlin);
        mList.add(vue);

        for (int i = 0; i < mTitles.length; i++) {
            mTabList.add(new MainTabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mBinding.tabLayout.setTabData(mTabList, this, R.id.frame_layout, mList);
    }
}

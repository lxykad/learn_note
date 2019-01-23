package com.article.demos.java.test;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.article.demos.common.base.IService;
import com.article.demos.common.constant.Constant;

/**
 * @author a
 */
@Route(path = Constant.WX_LOGIN)
public class WxTest implements IService{

    @Override
    public void init(Context context) {

    }

    /**
     * 测试代码
     * @return
     */
    @Override
    public String wxLogin() {

        return "wxlogin";
    }
}

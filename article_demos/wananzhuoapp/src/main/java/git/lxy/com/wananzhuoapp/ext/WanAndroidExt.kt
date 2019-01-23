package git.lxy.com.wananzhuoapp.ext

import android.arch.lifecycle.ViewModel
import android.widget.ImageView
import com.article.demos.common.base.BaseApplication
import com.article.demos.common.http.RetrofitFactory
import com.bumptech.glide.Glide
import git.lxy.com.wananzhuoapp.service.WanAndroidApiService


fun ViewModel.getApiService(): WanAndroidApiService {
    return RetrofitFactory.instance.create(WanAndroidApiService::class.java)
}

fun RetrofitFactory.getApiService(): WanAndroidApiService {
    return RetrofitFactory.instance.create(WanAndroidApiService::class.java)
}

fun ImageView.loadUrl(url: String) {
    Glide.with(BaseApplication.sContext).load(url).into(this)
}
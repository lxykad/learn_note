package git.lxy.com.wananzhuoapp.service

import com.article.demos.common.base.BaseBean
import git.lxy.com.wananzhuoapp.main.ArticleBean
import git.lxy.com.wananzhuoapp.main.BannerBean
import git.lxy.com.wananzhuoapp.main.Data
import git.lxy.com.wananzhuoapp.ui.home.CollectBean
import git.lxy.com.wananzhuoapp.ui.my.entity.CollectData
import git.lxy.com.wananzhuoapp.ui.my.entity.UserEntity
import git.lxy.com.wananzhuoapp.ui.nav.entity.NavigationData
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeChildBean
import git.lxy.com.wananzhuoapp.ui.tree.entity.TreeEntity
import git.lxy.com.wananzhuoapp.ui.wechat.bean.WeChatArticle
import git.lxy.com.wananzhuoapp.ui.wechat.bean.WeChatListBean
import io.reactivex.Observable
import retrofit2.http.*

/**
 *  @author liuxinyu
 *  @date 2018/8/20
 *  @Description wananzhuo api列表
 */
interface WanAndroidApiService {
    /**
     * 首页文章列表
     */
    @GET("/article/list/{page}/json/")
    fun getHomeArticleList(@Path("page") page: Int): Observable<BaseBean<Data>>

    @GET("/banner/json")
    fun getHomeBanner(): Observable<BaseBean<List<BannerBean>>>

    /**
     * 知识体系
     */
    @GET("/tree/json")
    fun getKnowledgeTree(): Observable<BaseBean<MutableList<TreeEntity>>>

    /**
     * 知识体系下的二级分类文章
     * http://www.wanandroid.com/article/list/0/json?cid=60
     */
    @GET("/article/list/{page}/json")
    fun getTreeChildList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseBean<TreeChildBean>>

    /**
     * 登录
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun login(
            @Field("username") username: String,
            @Field("password") password: String
    ): Observable<BaseBean<UserEntity>>

    /**
     * 注册
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun register(
            @Field("username") name: String,
            @Field("password") pwd: String,
            @Field("repassword") repwd: String
    ): Observable<BaseBean<UserEntity>>

    /**
     * 收藏站内文章
     */
    @POST("/lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Observable<BaseBean<CollectBean>>

    /**
     * 取消收藏---文章列表页
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollectArticle(@Path("id") id: Int): Observable<BaseBean<CollectBean>>

    /**
     * 取消收藏--我的收藏页面
     */
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun cancelMyCollect(@Path("id") id: Int,@Field("originId") originId: Int): Observable<BaseBean<CollectBean>>

    /**
     * 导航
     */
    @GET("/navi/json")
    fun getNavData(): Observable<BaseBean<List<NavigationData>>>

    /**
     * 获取公众号列表
     */
    @GET("/wxarticle/chapters/json")
    fun getWeChatList(): Observable<BaseBean<MutableList<WeChatListBean>>>

    /**
     * 查询某个公众号的历史数据
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun getWeChatHistoryList(@Path("id") id: Int, @Path("page") page: Int): Observable<BaseBean<WeChatArticle>>

    /**
     * 我的收藏列表
     */
    @GET("/lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int): Observable<BaseBean<CollectData>>

}
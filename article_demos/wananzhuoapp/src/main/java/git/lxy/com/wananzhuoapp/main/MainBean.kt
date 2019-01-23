package git.lxy.com.wananzhuoapp.main


data class MainBean(
        val data: Data,
        val errorCode: Int,
        val errorMsg: String
)

data class Data(
        val curPage: Int,
        val datas: List<ArticleBean>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
)


data class BannerBean(
        val desc: String,
        val id: Int,
        val imagePath: String,
        val isVisible: Int,
        val order: Int,
        val title: String,
        val type: Int,
        val url: String
)
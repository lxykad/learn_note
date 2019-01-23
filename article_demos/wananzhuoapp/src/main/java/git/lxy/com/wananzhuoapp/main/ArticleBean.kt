package git.lxy.com.wananzhuoapp.main

import java.io.Serializable

/**
 *  @author lxy
 *  @date 2018/8/20
 *  @Description 文章
 */

data class ArticleBean(
        val apkLink: String,
        val author: String,
        val chapterId: Int,
        val chapterName: String,
        var collect: Boolean,
        val courseId: Int,
        val desc: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val origin: String,
        val projectLink: String,
        val publishTime: Long,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
):Serializable

data class Tag(
        val name: String,
        val url: String
):Serializable
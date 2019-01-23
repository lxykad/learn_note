package git.lxy.com.wananzhuoapp.ui.my.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 *  @author liuxinyu
 *  @date 2018/9/6
 *  @Description 用户信息类
 */
@Entity(tableName = "user_info")
data class UserEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        @Ignore
        var collectIds: List<Int>,
        var email: String,
        var icon: String,
        var password: String,
        var token: String,
        var type: Int,
        var username: String
)

{
        constructor():this(0, listOf(),"","","","",0,"")
}
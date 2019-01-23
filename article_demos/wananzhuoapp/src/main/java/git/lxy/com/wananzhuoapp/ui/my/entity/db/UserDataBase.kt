package git.lxy.com.wananzhuoapp.ui.my.entity.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.article.demos.common.base.BaseApplication
import git.lxy.com.wananzhuoapp.ui.my.entity.UserEntity

/**
 *  @author liuxinyu
 *  @date 2018/9/6
 *  @Description 数据库抽象类
 */
@Database(entities = arrayOf(UserEntity::class), version = 1)
abstract class UserDataBase : RoomDatabase() {

    abstract fun userDao(): UserEntityDao

    companion object {
        @Volatile
        private var INSTANCE: UserDataBase? = null

        fun getInstance(): UserDataBase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase().also {
                        INSTANCE = it
                    }
                }

        private fun buildDatabase() = Room.databaseBuilder(BaseApplication.sContext,
                UserDataBase::class.java, "userInfo.db").build()
    }
}
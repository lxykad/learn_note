package git.lxy.com.wananzhuoapp.ui.my.entity.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import git.lxy.com.wananzhuoapp.ui.my.entity.UserEntity
import io.reactivex.Flowable

/**
 *  @author lxy
 *  @date 2018/9/6
 *  @Description
 */
@Dao
interface UserEntityDao {

    /**
     * 因为只有一个user 所以查询一个就行
     * 这里返回livedata类型的数据不行
     */
    @Query("select * from user_info limit 1")
    fun getUser(): LiveData<UserEntity>

    @Query("select * from user_info limit 1")
    fun getUser2(): Flowable<UserEntity>

    @Query("select* from user_info")
    fun getUserList(): List<UserEntity>

    @Query("select* from user_info where username like :name")
    fun getUserByName(name: String): UserEntity

    @Delete()
    fun deleteUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM user_info")
    fun delAllUsers()
}
package git.lxy.com.wananzhuoapp.util

import java.lang.reflect.ParameterizedType

/**
 *  @author liuxinyu
 *  @date 2018/9/20
 *  @Description
 */
class TUtil {

    companion object {

        fun <T> getNewInstance(obj: Any, i: Int): T? {

            obj?.let {
                return ((obj.javaClass
                        .genericSuperclass as ParameterizedType)
                        .actualTypeArguments[i] as Class<T>)
                        .newInstance()
            }
            return null
        }

        fun <T> getInstance(obj: Any, i: Int): T? {
            obj?.let {
                return (obj.javaClass
                        .genericSuperclass as ParameterizedType)
                        .actualTypeArguments[i] as T

            }

            return null
        }
    }
}
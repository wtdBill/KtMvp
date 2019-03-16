package ktmvp.yppcat.ktmvp.utils

import android.support.annotation.NonNull
import android.util.Log
import ktmvp.yppcat.ktmvp.BuildConfig

/**
 * Created by ypp0623 on 19-2-18.
 */
object Logger {
    private var isLog = BuildConfig.DEBUG
    private val tag: String = "ktmvp"
    private val max_str_length = 2001 - tag.length
    fun setTag(isLog: Boolean) {
        Logger.isLog = isLog
    }

    val get: Boolean
        get() = isLog

    fun d(@NonNull msg: String) {
        if (isLog) {
            Log.d(tag, msg)
        }
    }

    @JvmStatic
    fun e(@NonNull msg: String) {
        var mes = msg
        if (isLog) {
            while (mes.length> max_str_length){
                Log.e(tag,mes.substring(0, max_str_length))
                mes = mes.substring(max_str_length)
            }
            Log.e(tag, mes)
        }
    }

    fun i(@NonNull msg: String) {
        if (isLog) {
            Log.i(tag, msg)
        }
    }

    fun v(@NonNull msg: String) {
        if (isLog) {
            Log.v(tag, msg)
        }
    }

    fun w(@NonNull msg: String) {
        if (isLog) {
            Log.w(tag, msg)
        }
    }

}
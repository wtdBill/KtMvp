package ktmvp.yppcat.ktmvp.application

import android.app.Application
import android.content.Context
import android.view.WindowManager
import com.squareup.leakcanary.RefWatcher

import kotlin.properties.Delegates

@Suppress("DEPRECATION")
/**
 * Created by ypp0623 on 19-2-18.
 */

class MyApplication : Application() {
    private var refWatcher: RefWatcher? = null

    companion object {

        private val TAG = "MyApplication"

        var context: Context by Delegates.notNull()
            private set

        var screenHeight: Int by Delegates.notNull()
            private set
        var screenWidth: Int by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as MyApplication
            return myApplication.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        screenWidth = windowManager.defaultDisplay.width
        screenHeight = windowManager.defaultDisplay.height

    }
}

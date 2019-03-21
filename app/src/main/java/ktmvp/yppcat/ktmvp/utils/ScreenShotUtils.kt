package ktmvp.yppcat.ktmvp.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Rect

/**
 * Created by ypp0623 on 19-3-20.
 */
object ScreenShotUtils {

    @Suppress("DEPRECATION")
    fun screenShot(activity: Activity){
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val b1 = view.drawingCache

        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val  statusBarHeight = frame.top

        val  width = activity.windowManager.defaultDisplay.width
        val  height = activity.windowManager.defaultDisplay.height

        val  bitmap = Bitmap.createBitmap(b1,0,statusBarHeight,width,height-statusBarHeight)
        view.destroyDrawingCache()

        BitmapUtils.saveBitmapToLocal(bitmap)
    }
}
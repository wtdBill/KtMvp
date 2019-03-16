package ktmvp.yppcat.ktmvp.utils

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.PixelCopy
import ktmvp.yppcat.ktmvp.application.MyApplication
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by ypp0623 on 19-2-22.
 */
object BitmapUtils {

    fun saveBitmapToLocal(bitmap: Bitmap) {
        val file = File(FileConstants.IMAGE_PATH)
        if (!file.exists()) {
            file.mkdirs()
        }
        val f = File(FileConstants.IMAGE_PATH + File.separator + System.currentTimeMillis() + ".jpg")
        try {
            val out = FileOutputStream(f)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            //发送广播通知图库更新
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f))
            MyApplication.context.sendBroadcast(intent)
            MyApplication.context.showToast("保存成功")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun draweableTobitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }
}
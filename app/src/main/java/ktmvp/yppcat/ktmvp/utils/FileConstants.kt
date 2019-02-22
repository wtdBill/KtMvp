package ktmvp.yppcat.ktmvp.utils

import android.os.Environment
import ktmvp.yppcat.ktmvp.application.MyApplication
import java.io.File

/**
 * Created by ypp0623 on 19-2-22.
 */
object FileConstants {

    val IMAGE_PATH: String
        get() {
            return Environment.getExternalStorageDirectory().path + File.separator + Environment.DIRECTORY_DCIM + File.separator + "/Camera"
        }
}
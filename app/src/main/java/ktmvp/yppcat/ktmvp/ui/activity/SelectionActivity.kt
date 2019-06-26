package ktmvp.yppcat.ktmvp.ui.activity

import android.Manifest
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_selection.*
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.adapter.PicCursorAdapter
import ktmvp.yppcat.ktmvp.base.BaseActivity
import ktmvp.yppcat.ktmvp.data.IntentName
import ktmvp.yppcat.ktmvp.utils.Logger
import pub.devrel.easypermissions.EasyPermissions

@Route(path = IntentName.APP_ACTIVITY_SELECT)
@Suppress("DEPRECATION")
class SelectionActivity : BaseActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private val STORE_IMAGES = arrayOf(MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE, MediaStore.Images.Media._ID)
    private lateinit var adapter: PicCursorAdapter
    private val mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI!!

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        p1?.let {
            val value = it.get("key")
            Logger.e("value$value")
        }
        return CursorLoader(this, mImageUri, null, null, null, null)
    }

    override fun onLoadFinished(p0: Loader<Cursor>, p1: Cursor?) {
        adapter.swapCursor(p1)

    }

    override fun onLoaderReset(p0: Loader<Cursor>) {
        adapter.swapCursor(null)
    }

    override fun layoutId(): Int {
        return R.layout.activity_selection
    }

    override fun initData() {

    }

    override fun initView() {
        adapter = PicCursorAdapter(this, null, false)
        pic_list.adapter = adapter
        val parms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        EasyPermissions.requestPermissions(this, "ss", 0, *parms)
    }

    override fun start() {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == 0) {
            if (perms.isNotEmpty()) {
                if (perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE) && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    supportLoaderManager.initLoader(0, null, this)
                }
            }
        }
    }
}

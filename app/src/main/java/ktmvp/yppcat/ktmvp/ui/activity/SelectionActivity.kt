package ktmvp.yppcat.ktmvp.ui.activity

import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_selection.*
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.base.BaseActivity

class SelectionActivity : BaseActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private val STORE_IMAGES = arrayOf(MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE, MediaStore.Images.Media._ID)
    private val adapter: SimpleCursorAdapter? = null

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onLoadFinished(p0: Loader<Cursor>, p1: Cursor?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoaderReset(p0: Loader<Cursor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun layoutId(): Int {
        return R.layout.activity_selection
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun start() {

    }

}

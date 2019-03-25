package ktmvp.yppcat.ktmvp.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.data.IntentName
import ktmvp.yppcat.ktmvp.ui.view.FallObject
import ktmvp.yppcat.ktmvp.utils.BitmapUtils

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout.setScrimColor(Color.TRANSPARENT)

        mNaView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.joke -> ARouter.getInstance().build(IntentName.APP_ACTIVITY_JOKE).navigation()
                R.id.news -> ARouter.getInstance().build(IntentName.APP_ACTIVITY_NEWS).navigation()
                R.id.code -> ARouter.getInstance().build(IntentName.APP_ACTIVITY_DIMEN).navigation()
                R.id.Gongjiao1 -> ARouter.getInstance().build(IntentName.APP_ACTIVITY_SELECT).navigation()
            }
            true
        }
        val bitmap = BitmapUtils.draweableTobitmap(resources.getDrawable(R.drawable.sort))
        val fallObject = FallObject.Builder(bitmap)
                .setSize(true)
                .setSpeed(true)
                .build()
        mFallView.addFallObject(fallObject, 50)

        iv_header.setOnClickListener { mDrawerLayout.openDrawer(GravityCompat.START) }

    }


    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

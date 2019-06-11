package ktmvp.yppcat.ktmvp.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import cn.bmob.v3.util.V
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.header_layout.*
import ktmvp.yppcat.ktmvp.Bus.BaseMessage
import ktmvp.yppcat.ktmvp.Bus.KTBus
import ktmvp.yppcat.ktmvp.Bus.KTObserver
import ktmvp.yppcat.ktmvp.Bus.MessageType
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.data.IntentName
import ktmvp.yppcat.ktmvp.ui.view.FallObject
import ktmvp.yppcat.ktmvp.utils.BitmapUtils
import ktmvp.yppcat.ktmvp.utils.Constants
import ktmvp.yppcat.ktmvp.utils.Logger
import ktmvp.yppcat.ktmvp.utils.Preference

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), KTObserver {

    private lateinit var headerView: ImageView

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KTBus.instance.register(this)

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

        headerView = mNaView.getHeaderView(0).findViewById<ImageView>(R.id.icon_image)

        loadHead()

    }

    private fun loadHead() {
        val mHeaderImg by Preference(Constants.User.USER_HEAD, "")
        if (!TextUtils.isEmpty(mHeaderImg)) {
            val option = RequestOptions
                    .diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
            Glide.with(this)
                    .load(mHeaderImg)
                    .apply(option)
                    .into(headerView)
        }
    }


    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun handleMessage(message: BaseMessage) {
        when (message.getMessageType()) {
            MessageType.EXIT_APP -> System.exit(0)
            MessageType.CHANGE_HEAD -> loadHead()
        }
    }

    override fun onDestroy() {
        KTBus.instance.unRegister(this)
        super.onDestroy()
    }
}

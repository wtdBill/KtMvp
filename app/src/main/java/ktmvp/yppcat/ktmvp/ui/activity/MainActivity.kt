package ktmvp.yppcat.ktmvp.ui.activity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.view.GravityCompat
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*
import ktmvp.yppcat.ktmvp.Bus.BaseMessage
import ktmvp.yppcat.ktmvp.Bus.KTBus
import ktmvp.yppcat.ktmvp.Bus.KTObserver
import ktmvp.yppcat.ktmvp.Bus.MessageType
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.base.BaseActivity
import ktmvp.yppcat.ktmvp.data.IntentName
import ktmvp.yppcat.ktmvp.mvp.contract.MainContract
import ktmvp.yppcat.ktmvp.mvp.presenter.MainPresenter
import ktmvp.yppcat.ktmvp.ui.view.FallObject
import ktmvp.yppcat.ktmvp.utils.BitmapUtils

@Suppress("DEPRECATION")
class MainActivity : BaseActivity(), KTObserver, MainContract.View {

    private lateinit var headerView: ImageView

    private val mPresenter by lazy { MainPresenter() }

    init {
        mPresenter.attachView(this)
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        KTBus.instance.register(this)
    }

    override fun initView() {
        mDrawerLayout.setScrimColor(Color.TRANSPARENT)

        mNaView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.joke -> ARouter.getInstance().build(IntentName.APP_ACTIVITY_JOKE).navigation()
                R.id.news -> ARouter.getInstance().build(IntentName.APP_ACTIVITY_NEWS).navigation()
                R.id.code -> ARouter.getInstance().build(IntentName.APP_ACTIVITY_DIMEN).navigation()
                R.id.Gongjiao1 -> ARouter.getInstance().build(IntentName.APP_ACTIVITY_BLUETOOTH).navigation()
            }
            true
        }
        val bitmap = BitmapUtils.draweableTobitmap(resources.getDrawable(R.drawable.ic_xuehua))
        val fallObject = FallObject.Builder(bitmap)
                .setSize(true)
                .setSpeed(true)
                .setWind(5, true, true)
                .build()
        mFallView.addFallObject(fallObject, 50)

        iv_header.setOnClickListener {
            mDrawerLayout.openDrawer(GravityCompat.START)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showNotification()
            }
        }

        headerView = mNaView.getHeaderView(0).findViewById(R.id.icon_image)
        headerView.setOnClickListener {
            ARouter.getInstance().build(IntentName.APP_ACTIVITY_SELECT).navigation()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = Notification.Builder(this.applicationContext)
                .setSound(MediaStore.Audio.Media.INTERNAL_CONTENT_URI)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("有消息")
                .setContentTitle("title")
                .setContentText("content")
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setChannelId(this.packageName)
                .setOngoing(false)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)

        val channel = NotificationChannel(this.packageName,"message",NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)

        val intent = Intent(this, SelectionActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val resultIntent = PendingIntent.getActivity(this, 5, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(resultIntent)
        manager.notify(1, builder.build())
    }

    override fun start() {
        mPresenter.loadHead(this, headerView)
    }


    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun showDefault() {

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
            MessageType.CHANGE_HEAD -> mPresenter.loadHead(this, headerView)
        }
    }

    override fun onDestroy() {
        KTBus.instance.unRegister(this)
        super.onDestroy()
    }
}

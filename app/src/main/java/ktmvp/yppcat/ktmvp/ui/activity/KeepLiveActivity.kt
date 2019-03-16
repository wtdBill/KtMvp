package ktmvp.yppcat.ktmvp.ui.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.view.Gravity
import ktmvp.yppcat.ktmvp.utils.Constants

class KeepLiveActivity : AppCompatActivity() {

    private lateinit var endReceiver: BroadcastReceiver
    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        window.setGravity(Gravity.LEFT or Gravity.TOP)
        val params = window.attributes
        params.x = 0
        params.y = 0
        params.height = 1
        params.width = 1
        window.attributes = params

        endReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                finish()
            }
        }
        registerReceiver(endReceiver, IntentFilter(Constants.FINISH_LIVE_ACTIVITY))
    }

    override fun onResume() {
        super.onResume()
        checkScreen()
    }

    override fun onDestroy() {
        super.onDestroy()
        endReceiver.let { unregisterReceiver(it) }
    }

    private fun checkScreen() {
        val pm: PowerManager = this@KeepLiveActivity.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (pm.isScreenOn) {
            finish()
        }
    }
}

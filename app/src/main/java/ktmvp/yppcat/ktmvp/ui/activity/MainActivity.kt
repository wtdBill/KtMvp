package ktmvp.yppcat.ktmvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import ktmvp.yppcat.ktmvp.R

class MainActivity : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout.setScrimColor(Color.TRANSPARENT)

        mNaView.setNavigationItemSelectedListener {
           when(it.itemId){
               R.id.joke -> startActivity(Intent(this@MainActivity,JokeActivity::class.java))
               R.id.news -> startActivity(Intent(this@MainActivity,NewsActivity::class.java))
               R.id.code -> startActivity(Intent(this@MainActivity,DimensionCodeActivity::class.java))
           }
            true
        }

        iv_header.setOnClickListener { mDrawerLayout.openDrawer(GravityCompat.START) }

//        Handler().postDelayed({
//            startActivity(Intent(this, DimensionCodeActivity::class.java))
//        }, 2000)
    }



    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed();
        }
    }
}

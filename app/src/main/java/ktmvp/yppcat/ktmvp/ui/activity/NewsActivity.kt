package ktmvp.yppcat.ktmvp.ui.activity

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_news.*
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.base.BaseActivity
import ktmvp.yppcat.ktmvp.data.IntentName
import ktmvp.yppcat.ktmvp.ui.fragment.NewsFragment
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

@Route(path = IntentName.APP_ACTIVITY_NEWS)
class NewsActivity : BaseActivity() {

    private lateinit var adapter: MyAdapter

    companion object {
        private val typeArray = arrayOf("top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang")
        private val typeText = arrayOf("头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚")
    }

    override fun layoutId(): Int {
        return R.layout.activity_news
    }

    override fun initData() {
        mContext = this
        adapter = MyAdapter(supportFragmentManager)
        mViewPager.adapter = adapter
        mViewPager.offscreenPageLimit = 1
        initIndicator()
    }

    override fun initView() {

    }

    override fun start() {

    }


    private fun initIndicator() {
        val commonNavigator = CommonNavigator(mContext)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(p0: Context?, p1: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(mContext)
                colorTransitionPagerTitleView.normalColor = Color.GRAY
                colorTransitionPagerTitleView.selectedColor = Color.BLACK
                colorTransitionPagerTitleView.text = typeText[p1]
                colorTransitionPagerTitleView.setOnClickListener {
                    mViewPager.currentItem = p1
                    adapter.notifyDataSetChanged()
                }
                return colorTransitionPagerTitleView
            }


            override fun getCount(): Int {
                return typeText.size
            }

            override fun getIndicator(p0: Context?): IPagerIndicator? {
                val indicator = LinePagerIndicator(mContext)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                return indicator
            }
        }
        indicator.navigator = commonNavigator
        ViewPagerHelper.bind(indicator, mViewPager)
    }

    internal inner class MyAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        override fun getItem(p0: Int): Fragment {
            return NewsFragment.Instance(typeArray[p0])
        }

        override fun getCount(): Int {
            return typeArray.size
        }

    }
}

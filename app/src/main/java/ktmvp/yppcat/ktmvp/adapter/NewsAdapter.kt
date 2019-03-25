package ktmvp.yppcat.ktmvp.adapter

import android.content.Intent
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.data.IntentName
import ktmvp.yppcat.ktmvp.network.bean.NewsData
import ktmvp.yppcat.ktmvp.ui.activity.WebViewActivity

/**
 * Created by ypp0623 on 19-2-20.
 */
class NewsAdapter(layoutResId: Int, data: MutableList<NewsData.Result.Data>?) : BaseQuickAdapter<NewsData.Result.Data, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: NewsData.Result.Data?) {
        item?.title?.let { ktmvp.yppcat.ktmvp.utils.Logger.e(it) }
        helper!!.getView<TextView>(R.id.title).text = item!!.title
        helper.getView<TextView>(R.id.author).text = item.author_name
        Glide.with(mContext).load(item.thumbnail_pic_s).into(helper.getView(R.id.iv1))
        Glide.with(mContext).load(item.thumbnail_pic_s02).into(helper.getView(R.id.iv2))
        Glide.with(mContext).load(item.thumbnail_pic_s03).into(helper.getView(R.id.iv3))
        helper.getView<LinearLayout>(R.id.rootLayout).setOnClickListener {
            ARouter.getInstance().build(IntentName.APP_ACTIVITY_WEBVIEW)
                    .withString(WebViewActivity.PARAM_URL,item.url)
                    .navigation()
        }
    }
}
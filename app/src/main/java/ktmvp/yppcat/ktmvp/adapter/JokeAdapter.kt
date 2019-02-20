package ktmvp.yppcat.ktmvp.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.network.bean.JokeData

/**
 * Created by ypp0623 on 19-2-19.
 */
class JokeAdapter(layoutResId: Int, data: MutableList<JokeData.Result.Data>?) : BaseQuickAdapter<JokeData.Result.Data, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: JokeData.Result.Data) {
        helper?.getView<TextView>(R.id.text)?.text = item.content
    }
}
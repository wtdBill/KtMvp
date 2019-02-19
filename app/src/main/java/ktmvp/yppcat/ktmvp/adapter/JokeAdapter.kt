package ktmvp.yppcat.ktmvp.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.ypp.life.network.entity.JokeEntity
import kotlinx.android.synthetic.main.joke_item.view.*
import ktmvp.yppcat.ktmvp.R

/**
 * Created by ypp0623 on 19-2-19.
 */
class JokeAdapter(layoutResId: Int, data: MutableList<JokeEntity.ResultBean.DataBean>?) : BaseQuickAdapter<JokeEntity.ResultBean.DataBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: JokeEntity.ResultBean.DataBean?) {
        helper?.getView<TextView>(R.id.text)?.text = item?.content
    }
}
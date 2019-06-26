package ktmvp.yppcat.ktmvp.adapter

import android.bluetooth.BluetoothDevice
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ktmvp.yppcat.ktmvp.R

class BlueToothAdapter(layoutResId : Int,data:MutableList<BluetoothDevice>) : BaseQuickAdapter<BluetoothDevice, BaseViewHolder>(layoutResId,data) {
    override fun convert(helper: BaseViewHolder?, item: BluetoothDevice?) {
        helper?.getView<TextView>(R.id.name)?.text = item?.name
        helper?.getView<TextView>(R.id.id)?.text = item?.address
    }
}
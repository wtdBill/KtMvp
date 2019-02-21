package ktmvp.yppcat.ktmvp.ui.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_dimension_code.*
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.base.BaseActivity
import ktmvp.yppcat.ktmvp.mvp.contract.DimenContract
import ktmvp.yppcat.ktmvp.mvp.presenter.DimenPresenter
import ktmvp.yppcat.ktmvp.utils.Logger

class DimensionCodeActivity : BaseActivity(), DimenContract.View {

    private val mPresenter by lazy { DimenPresenter() }

    private var bitmap: Bitmap? = null

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun setDinemData(data: String) {
        try {
            val bytes = Base64.decode(data, Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            mDimenPic.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showError(errorMessage: String) {

    }

    override fun layoutId(): Int {
        return R.layout.activity_dimension_code
    }

    override fun initData() {
        mContext = this
    }

    override fun initView() {
        mPresenter.attachView(this)
        confirm.setOnClickListener { mPresenter.getDimenData(mInputText.text.toString()) }

    }

    override fun start() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
        bitmap?.recycle()
    }
}

package ktmvp.yppcat.ktmvp.ui.activity

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_dimension_code.*
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.base.BaseActivity
import ktmvp.yppcat.ktmvp.data.IntentName
import ktmvp.yppcat.ktmvp.mvp.contract.DimenContract
import ktmvp.yppcat.ktmvp.mvp.presenter.DimenPresenter
import ktmvp.yppcat.ktmvp.utils.BitmapUtils
import pub.devrel.easypermissions.EasyPermissions

@Route(path = IntentName.APP_ACTIVITY_DIMEN)
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
        ARouter.getInstance().inject(this)
        mContext = this
    }

    override fun initView() {
        mPresenter.attachView(this)
        openKeyBord(mInputText, mContext)
        confirm.setOnClickListener {
            closeKeyBord(mInputText, mContext)
            mPresenter.getDimenData(mInputText.text.toString())
        }
        save.setOnClickListener {
            val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            EasyPermissions.requestPermissions(this, "KtMvp应用需要以下权限，请允许", 0, *perms)
        }

    }

    override fun start() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
        bitmap?.recycle()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        super.onPermissionsGranted(requestCode, perms)
        bitmap?.let { BitmapUtils.saveBitmapToLocal(it) }
    }
}

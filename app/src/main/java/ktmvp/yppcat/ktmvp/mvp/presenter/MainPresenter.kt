package ktmvp.yppcat.ktmvp.mvp.presenter

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ktmvp.yppcat.ktmvp.base.BasePrenster
import ktmvp.yppcat.ktmvp.mvp.contract.MainContract
import ktmvp.yppcat.ktmvp.utils.Constants
import ktmvp.yppcat.ktmvp.utils.Preference

@Suppress("CAST_NEVER_SUCCEEDS")
class MainPresenter : BasePrenster<MainContract.View>(),MainContract.Presenter {

    override fun loadHead(context: Context,view: ImageView) {

        val mHeaderImg by Preference(Constants.User.USER_HEAD, "")
        if (!TextUtils.isEmpty(mHeaderImg)) {
            val option = RequestOptions
                    .diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
            Glide.with(context)
                    .load(mHeaderImg)
                    .apply(option)
                    .into(view)
        }else{
            this.mRootView?.showDefault()
        }
    }
}
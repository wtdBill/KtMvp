package ktmvp.yppcat.ktmvp.mvp.contract

import android.content.Context
import android.widget.ImageView
import ktmvp.yppcat.ktmvp.base.IBaseView
import ktmvp.yppcat.ktmvp.base.IPrenster

class MainContract {

    interface View : IBaseView {

        fun showDefault()
    }

    interface Presenter : IPrenster<View> {

        fun loadHead(context : Context,view : ImageView)
    }
}
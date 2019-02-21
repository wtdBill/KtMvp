package ktmvp.yppcat.ktmvp.mvp.contract

import ktmvp.yppcat.ktmvp.base.IBaseView
import ktmvp.yppcat.ktmvp.base.IPrenster

/**
 * Created by ypp0623 on 19-2-21.
 */
interface DimenContract {

    interface View : IBaseView {

        fun setDinemData(data: String)

        fun showError(errorMessage: String)
    }

    interface Presenter : IPrenster<View> {

        fun getDimenData(text: String)
    }
}
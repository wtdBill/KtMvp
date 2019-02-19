package ktmvp.yppcat.ktmvp.base

import android.view.View

/**
 * Created by ypp0623 on 19-2-18.
 */
interface IPrenster<in V : IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()
}
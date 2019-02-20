package ktmvp.yppcat.ktmvp.mvp.contract

import ktmvp.yppcat.ktmvp.base.IBaseView
import ktmvp.yppcat.ktmvp.base.IPrenster
import ktmvp.yppcat.ktmvp.network.bean.NewsData

/**
 * Created by ypp0623 on 19-2-20.
 */
interface NewsContract {

    interface View : IBaseView {

        fun setNewsList(itemList: ArrayList<NewsData.Result.Data>)

        fun showError(errorMessage: String, errorCode: Int)
    }

    interface Presenter : IPrenster<View> {

        fun getNewsList(type: String, key: String)

    }
}
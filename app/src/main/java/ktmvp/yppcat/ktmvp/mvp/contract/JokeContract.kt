package ktmvp.yppcat.ktmvp.mvp.contract

import ktmvp.yppcat.ktmvp.base.IBaseView
import ktmvp.yppcat.ktmvp.base.IPrenster
import ktmvp.yppcat.ktmvp.network.bean.JokeData

/**
 * Created by ypp0623 on 19-2-19.
 */
interface JokeContract {

    interface View : IBaseView {

        fun setJokeList(itemList: ArrayList<JokeData.Result.Data>)

        fun showError(errorMessage: String, errorCode: Int)
    }

    interface Presenter : IPrenster<View> {

        fun getJokeList(sort: String, page: Int, pageSize: Int, key: String)

        fun loadMoreData(sort: String, page: Int, pageSize: Int, key: String)
    }
}
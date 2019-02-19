package ktmvp.yppcat.ktmvp.mvp.contract

import com.example.ypp.life.network.entity.JokeEntity
import ktmvp.yppcat.ktmvp.base.IBaseView
import ktmvp.yppcat.ktmvp.base.IPrenster

/**
 * Created by ypp0623 on 19-2-19.
 */
interface JokeContract {

    interface View : IBaseView {

        fun setJokeList(itemList: ArrayList<JokeEntity.ResultBean.DataBean>)

        fun showError(errorMessage: String,errorCode:Int)
    }

    interface Presenter : IPrenster<View> {

        fun getJokeList(sort: String, page: Int, pageSize: Int, time: String, key: String)

        fun loadMoreData(sort: String, page: Int, pageSize: Int, time: String, key: String)
    }
}
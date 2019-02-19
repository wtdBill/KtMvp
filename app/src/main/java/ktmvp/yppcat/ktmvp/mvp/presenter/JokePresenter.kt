package ktmvp.yppcat.ktmvp.mvp.presenter

import com.example.ypp.life.network.entity.JokeEntity
import ktmvp.yppcat.ktmvp.base.BasePrenster
import ktmvp.yppcat.ktmvp.mvp.contract.JokeContract
import ktmvp.yppcat.ktmvp.mvp.model.JokeModel
import ktmvp.yppcat.ktmvp.network.exception.ExceptionHandle
import ktmvp.yppcat.ktmvp.utils.Constants
import ktmvp.yppcat.ktmvp.utils.Preference

/**
 * Created by ypp0623 on 19-2-19.
 */
class JokePresenter : BasePrenster<JokeContract.View>(), JokeContract.Presenter {

    private val jokeModel by lazy {
        JokeModel()
    }

    override fun getJokeList(sort: String, page: Int, pageSize: Int, time: String, key: String) {
        checkViewAttached()
        val disposable = jokeModel.getJoke(sort, page, pageSize, time, key)
                .subscribe({ it ->
                    mRootView?.apply {
                        it.result?.data?.let { it1 ->
                            setJokeList(it1 as ArrayList<JokeEntity.ResultBean.DataBean>)
                            Preference(Constants.JOKE_TIME, it.result?.data?.last()?.unixtime.toString())
                        }
                    }
                }, { t: Throwable? ->
                    mRootView?.apply {
                        t?.let { ExceptionHandle.handleException(it) }?.let { showError(it, ExceptionHandle.errorCode) }
                    }
                })
        addSubsciption(disposable)
    }

    override fun loadMoreData(sort: String, page: Int, pageSize: Int, time: String, key: String) {
        checkViewAttached()
        val disposable = jokeModel.getJoke(sort, page, pageSize, time, key)
                .subscribe({ it ->
                    mRootView?.apply {
                        it.result?.data?.let { it1 ->
                            setJokeList(it1 as ArrayList<JokeEntity.ResultBean.DataBean>)
                            Preference(Constants.JOKE_TIME, it.result?.data?.last()?.unixtime.toString())
                        }
                    }
                }, { t: Throwable? ->
                    mRootView?.apply {
                        t?.let { ExceptionHandle.handleException(it) }?.let { showError(it, ExceptionHandle.errorCode) }
                    }
                })
        disposable?.let { addSubsciption(it) }
    }
}
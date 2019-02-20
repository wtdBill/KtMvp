package ktmvp.yppcat.ktmvp.mvp.presenter

import ktmvp.yppcat.ktmvp.application.MyApplication
import ktmvp.yppcat.ktmvp.base.BasePrenster
import ktmvp.yppcat.ktmvp.mvp.contract.JokeContract
import ktmvp.yppcat.ktmvp.mvp.model.JokeModel
import ktmvp.yppcat.ktmvp.network.bean.JokeData
import ktmvp.yppcat.ktmvp.network.exception.ExceptionHandle
import ktmvp.yppcat.ktmvp.utils.Constants
import ktmvp.yppcat.ktmvp.utils.Logger
import ktmvp.yppcat.ktmvp.utils.Preference

@Suppress("UNCHECKED_CAST")
/**
 * Created by ypp0623 on 19-2-19.
 */
class JokePresenter : BasePrenster<JokeContract.View>(), JokeContract.Presenter {

    private var time: String by Preference(Constants.JOKE_TIME, Constants.DEFAULT_JOKE_TIME)

    private val jokeModel by lazy {
        JokeModel()
    }

    override fun getJokeList(sort: String, page: Int, pageSize: Int, key: String) {
        checkViewAttached()
        Logger.e(time)
        var tm = time.substring(0, 9)
        tm += time[9]
        val disposable = jokeModel.getJoke(sort, page, pageSize, tm, key)
                .subscribe({ it ->
                    mRootView?.apply {
                        it.result?.data?.let { it1 ->
                            setJokeList(it1 as ArrayList<JokeData.Result.Data>)
                            time = it.result.data.last()?.unixtime.toString()

                        }
                    }
                }, { t: Throwable? ->
                    mRootView?.apply {
                        t?.let { ExceptionHandle.handleException(it) }?.let { showError(it, ExceptionHandle.errorCode) }
                    }
                })
        addSubsciption(disposable)
    }

    override fun loadMoreData(sort: String, page: Int, pageSize: Int, key: String) {
        Logger.e(time)
        checkViewAttached()
        val disposable = jokeModel.getJoke(sort, page, pageSize, time, key)
                .subscribe({ it ->
                    mRootView?.apply {
                        it.result?.data?.let { it1 ->
                            setJokeList(it1 as ArrayList<JokeData.Result.Data>)
                            time = it.result.data.last()?.unixtime.toString()
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
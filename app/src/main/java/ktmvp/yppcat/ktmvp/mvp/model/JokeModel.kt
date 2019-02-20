package ktmvp.yppcat.ktmvp.mvp.model

import io.reactivex.Observable
import ktmvp.yppcat.ktmvp.network.RetrofitManager
import ktmvp.yppcat.ktmvp.network.bean.JokeData
import ktmvp.yppcat.ktmvp.scheduler.SchedulerUtils

/**
 * Created by ypp0623 on 19-2-19.
 */
class JokeModel {

    fun getJoke(sort: String, page: Int, pageSize: Int, time: String, key: String): Observable<JokeData> {
        return RetrofitManager.juheService.getJoke(sort, page, pageSize, time, key)
                .compose(SchedulerUtils.ioToMain())
    }
}
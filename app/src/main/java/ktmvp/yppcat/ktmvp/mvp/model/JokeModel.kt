package ktmvp.yppcat.ktmvp.mvp.model

import com.example.ypp.life.network.entity.JokeEntity
import io.reactivex.Observable
import ktmvp.yppcat.ktmvp.network.RetrofitManager
import ktmvp.yppcat.ktmvp.scheduler.SchedulerUtils
import java.util.*

/**
 * Created by ypp0623 on 19-2-19.
 */
class JokeModel {

    fun getJoke(sort: String, page: Int, pageSize: Int, time: String, key: String): Observable<JokeEntity> {
        return RetrofitManager.juheService.getJoke(sort, page, pageSize, time, key)
                .compose(SchedulerUtils.ioToMain())
    }
}
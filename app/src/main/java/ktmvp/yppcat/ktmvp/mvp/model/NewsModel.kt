package ktmvp.yppcat.ktmvp.mvp.model

import io.reactivex.Observable
import io.reactivex.Scheduler
import ktmvp.yppcat.ktmvp.network.RetrofitManager
import ktmvp.yppcat.ktmvp.network.bean.NewsData
import ktmvp.yppcat.ktmvp.scheduler.SchedulerUtils

/**
 * Created by ypp0623 on 19-2-20.
 */
class NewsModel {

    fun getNews(type: String, key: String): Observable<NewsData> {
        return RetrofitManager.juheService.getNews(type, key)
                .compose(SchedulerUtils.ioToMain())

    }
}
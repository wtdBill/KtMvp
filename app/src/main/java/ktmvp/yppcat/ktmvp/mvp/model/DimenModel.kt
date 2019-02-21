package ktmvp.yppcat.ktmvp.mvp.model

import io.reactivex.Observable
import ktmvp.yppcat.ktmvp.application.MyApplication
import ktmvp.yppcat.ktmvp.network.RetrofitManager
import ktmvp.yppcat.ktmvp.network.bean.DimenData
import ktmvp.yppcat.ktmvp.scheduler.SchedulerUtils
import ktmvp.yppcat.ktmvp.utils.Constants
import ktmvp.yppcat.ktmvp.utils.dip2px

/**
 * Created by ypp0623 on 19-2-21.
 */
class DimenModel {

    fun getDimenData(text: String): Observable<DimenData> {
        return RetrofitManager.juheService.getDimenData(text, MyApplication.context.dip2px(200f), Constants.JUHE_DIMEN_KEY)
                .compose(SchedulerUtils.ioToMain())
    }
}
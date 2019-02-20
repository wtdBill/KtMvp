package ktmvp.yppcat.ktmvp.mvp.presenter

import ktmvp.yppcat.ktmvp.base.BasePrenster
import ktmvp.yppcat.ktmvp.mvp.contract.NewsContract
import ktmvp.yppcat.ktmvp.mvp.model.NewsModel
import ktmvp.yppcat.ktmvp.network.bean.NewsData
import ktmvp.yppcat.ktmvp.network.exception.ExceptionHandle
import ktmvp.yppcat.ktmvp.utils.Logger

@Suppress("UNCHECKED_CAST")
/**
 * Created by ypp0623 on 19-2-20.
 */
class NewsPresenter : BasePrenster<NewsContract.View>(), NewsContract.Presenter {

    private val newsModel by lazy { NewsModel() }

    override fun getNewsList(type: String, key: String) {
        val disposable = newsModel.getNews(type, key)
                .subscribe({ it ->
                    mRootView?.apply {
                        Logger.e("jiazai jieshu")
                        setNewsList(it.result?.data as ArrayList<NewsData.Result.Data>)
                    }
                }, { t ->
                    mRootView.apply {
                        t?.let { ExceptionHandle.handleException(it) }?.let { this?.showError(it, ExceptionHandle.errorCode) }
                    }
                })
        addSubsciption(disposable)
    }


}
package ktmvp.yppcat.ktmvp.mvp.presenter

import ktmvp.yppcat.ktmvp.base.BasePrenster
import ktmvp.yppcat.ktmvp.mvp.contract.DimenContract
import ktmvp.yppcat.ktmvp.mvp.model.DimenModel
import ktmvp.yppcat.ktmvp.network.exception.ExceptionHandle

/**
 * Created by ypp0623 on 19-2-21.
 */
class DimenPresenter : BasePrenster<DimenContract.View>(), DimenContract.Presenter {

    private val dimenModel by lazy { DimenModel() }

    override fun getDimenData(text: String) {

        val disposable = dimenModel.getDimenData(text)
                .subscribe({ it ->
                    mRootView.apply {
                        it.result?.base64_image?.let { it1 -> this?.setDinemData(it1) }
                    }
                }, { t ->
                    mRootView.apply {
                        t?.let { ExceptionHandle.handleException(it) }?.let { this?.showError(it) }
                    }
                })
        addSubsciption(disposable)
    }
}
package ktmvp.yppcat.ktmvp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by ypp0623 on 19-2-18.
 */
open class BasePrenster<T : IBaseView> : IPrenster<T> {
    var mRootView: T? = null
        private set

    private var compositeDisposable = CompositeDisposable()

    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    fun addSubsciption(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private val isViewAttached: Boolean
        get() = mRootView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
}
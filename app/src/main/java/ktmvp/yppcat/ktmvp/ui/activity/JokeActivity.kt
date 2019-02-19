package ktmvp.yppcat.ktmvp.ui.activity

import android.net.sip.SipErrorCode
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.ypp.life.network.entity.JokeEntity
import kotlinx.android.synthetic.main.activity_joke.*
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.adapter.JokeAdapter
import ktmvp.yppcat.ktmvp.base.BaseActivity
import ktmvp.yppcat.ktmvp.mvp.contract.JokeContract
import ktmvp.yppcat.ktmvp.mvp.presenter.JokePresenter
import ktmvp.yppcat.ktmvp.utils.Constants
import ktmvp.yppcat.ktmvp.utils.Logger
import ktmvp.yppcat.ktmvp.utils.Preference

class JokeActivity : BaseActivity(), JokeContract.View {

    private val count = 20
    private var page = 1
    private val type_after = "asc"
    private val type_before = "desc"

    private val mPresenter by lazy { JokePresenter() }

    private val itemList: ArrayList<JokeEntity.ResultBean.DataBean> = ArrayList()

    private val mAdapter by lazy { JokeAdapter(R.layout.joke_item, itemList) }

    companion object {
        private var time: String = System.currentTimeMillis().toString()
    }

    init {
        mPresenter.attachView(this)
    }

    private var loadMore = false

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun setJokeList(itemList: ArrayList<JokeEntity.ResultBean.DataBean>) {
        mAdapter.loadMoreComplete()
        loadMore = false
        this.itemList.addAll(itemList)
        mAdapter.notifyDataSetChanged()
    }

    override fun showError(errorMessage: String, errorCode: Int) {
        mStatusView.showError()
    }


    override fun layoutId(): Int {
        return R.layout.activity_joke
    }

    override fun initData() {

    }

    override fun initView() {
        mLayoutStatusView = mStatusView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
        mAdapter.setOnLoadMoreListener({
            if (!loadMore) {
                loadMore = true
                start()
            }
        })
    }

    override fun start() {
        time = Preference(Constants.JOKE_TIME, "1418745237").toString()
        var tm = time.substring(0, 9)
        tm += time[9]
        Logger.e(tm)
        mPresenter.getJokeList(type_after, page, count, "1418745237", Constants.JUHE_JOKE_KEY)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}

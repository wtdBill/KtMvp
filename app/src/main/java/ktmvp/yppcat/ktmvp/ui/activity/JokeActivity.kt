package ktmvp.yppcat.ktmvp.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_joke.*
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.adapter.JokeAdapter
import ktmvp.yppcat.ktmvp.base.BaseActivity
import ktmvp.yppcat.ktmvp.data.IntentName
import ktmvp.yppcat.ktmvp.mvp.contract.JokeContract
import ktmvp.yppcat.ktmvp.mvp.presenter.JokePresenter
import ktmvp.yppcat.ktmvp.network.bean.JokeData
import ktmvp.yppcat.ktmvp.utils.Constants

@Route(path = IntentName.APP_ACTIVITY_JOKE)
class JokeActivity : BaseActivity(), JokeContract.View {

    private val count = 20
    private var page = 1
    private val type_after = "asc"
    private val type_before = "desc"

    private val mPresenter by lazy { JokePresenter() }

    private val itemList: ArrayList<JokeData.Result.Data> = ArrayList()

    private val mAdapter by lazy { JokeAdapter(R.layout.joke_item, itemList) }

    init {
        mPresenter.attachView(this)
    }

    private var loadMore = false

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun setJokeList(itemList: ArrayList<JokeData.Result.Data>) {
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
        mPresenter.getJokeList(type_after, page, count, Constants.JUHE_JOKE_KEY)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}

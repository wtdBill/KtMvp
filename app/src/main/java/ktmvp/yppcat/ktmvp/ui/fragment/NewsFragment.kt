package ktmvp.yppcat.ktmvp.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_joke.*
import kotlinx.android.synthetic.main.fragment_news.*

import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.adapter.NewsAdapter
import ktmvp.yppcat.ktmvp.base.BaseFragment
import ktmvp.yppcat.ktmvp.mvp.contract.NewsContract
import ktmvp.yppcat.ktmvp.mvp.presenter.NewsPresenter
import ktmvp.yppcat.ktmvp.network.bean.NewsData
import ktmvp.yppcat.ktmvp.utils.Constants
import ktmvp.yppcat.ktmvp.utils.Logger


/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : BaseFragment(), NewsContract.View {

    companion object {
        fun Instance(type: String): NewsFragment {
            val newsFragment = NewsFragment()
            val bundle = Bundle()
            bundle.putString("type", type)
            newsFragment.arguments = bundle
            return newsFragment
        }
    }

    private val mPresenter by lazy { NewsPresenter() }
    private val newsArray: ArrayList<NewsData.Result.Data> = ArrayList()
    private lateinit var mAdapter: NewsAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_news
    }

    override fun initView() {
        val type = arguments?.getString("type")
        Logger.e(type.toString())
        mAdapter = NewsAdapter(R.layout.layout, newsArray)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mAdapter

        mPresenter.getNewsList(type!!, Constants.JUHE_NEWS_KEY)
    }

    override fun lazyLoad() {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun setNewsList(itemList: ArrayList<NewsData.Result.Data>) {
        Logger.e("jiazai jieshu")
        newsArray.addAll(itemList)
        mAdapter.notifyDataSetChanged()
    }

    override fun showError(errorMessage: String, errorCode: Int) {
        statusView.showError()
    }


}

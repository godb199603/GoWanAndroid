package com.example.gowanandroid.ui.home.popular

import androidx.lifecycle.Observer
import com.example.gowanandroid.R
import com.example.gowanandroid.model.LoadMoreStatus
import com.example.gowanandroid.ui.base.BaseVmFragment
import com.example.gowanandroid.ui.detail.DetailActivity
import com.example.gowanandroid.ui.home.ArticleAdapter
import com.example.gowanandroid.util.ActivityManager
import com.example.gowanandroid.util.bus.Bus
import com.example.gowanandroid.util.bus.USER_COLLECT_UPDATED
import com.example.gowanandroid.util.bus.USER_LOGIN_STATE_CHANGED

import kotlinx.android.synthetic.main.fragment_popular.*

class PopularFragment  : BaseVmFragment<PopularViewModel>(){
    companion object {
        fun newInstance() = PopularFragment()
    }


    private lateinit var mAdapter: ArticleAdapter
    override fun layoutRes() = R.layout.fragment_popular
    override fun viewModelClass() = PopularViewModel::class.java

    override fun initView() {
        refreshLayout.run{
            setOnRefreshListener{mViewModel.refreshArticleList()}
//            setOnLoadMoreListener{mViewModel.loadMoreArticleList()}

        }
            mAdapter = ArticleAdapter(R.layout.item_article)
                .apply {
                bindToRecyclerView(recyclerView)
                setOnLoadMoreListener({
                    mViewModel.loadMoreArticleList()
                }, recyclerView)
                    setOnItemClickListener { _, _, position ->
                        val article = mAdapter.data[position]
                        ActivityManager.start(
                            DetailActivity::class.java,
                            mapOf(DetailActivity.PARAM_ARTICLE to article)
                        )
                    }
                    setOnItemChildClickListener { _, view, position ->
                        val article = mAdapter.data[position]
                        if (view.id == R.id.iv_collect && checkLogin()) {
                            view.isSelected = !view.isSelected
                            if (article.collect) {
                                mViewModel.uncollect(article.id)
                            } else {
                                mViewModel.collect(article.id)
                            }
                        }
                    }
            }
    }


    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                refreshLayout.setEnableRefresh(it)
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })

            Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner) {
                mViewModel.updateListCollectState()
            }

            Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATED, viewLifecycleOwner) {
                mViewModel.updateItemCollectState(it)
            }


        }
    }


    override fun lazyLoadData() {
        mViewModel.refreshArticleList()
    }

}

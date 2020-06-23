package com.hdsx.x1.ui.search.result

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.hdsx.R
import com.hdsx.x1.common.loadmore.CommonLoadMoreView
import com.hdsx.x1.common.loadmore.LoadMoreStatus
import com.hdsx.x1.ui.base.BaseVmFragment
import com.hdsx.x1.ui.detail.DetailActivity
import com.hdsx.x1.ui.main.home.ArticleAdapter
import com.hdsx.x1.util.core.ActivityManager
import com.hdsx.x1.util.core.bus.Bus
import com.hdsx.x1.util.core.bus.USER_COLLECT_UPDATED
import com.hdsx.x1.util.core.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.include_reload.*
import kotlinx.android.synthetic.main.fragment_search_result.*


class SearchResultFragment : BaseVmFragment<SearchResultViewModel>() {

    companion object {
        fun newInstance(): SearchResultFragment {
            return SearchResultFragment();
        }
    }

    private lateinit var searchResultAdapter: ArticleAdapter

    override fun layoutRes() = R.layout.fragment_search_result

    override fun viewModelClass() = SearchResultViewModel::class.java

    override fun initView() {
        searchResultAdapter = ArticleAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = data[position]
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
                val article = data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }
            }
            setOnLoadMoreListener({ mViewModel.loadMore() }, recyclerView)
        }

        // 刷新空间配置 颜色，和刷新事件
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.search() }
        }

        // 重新加载的按钮事件
        btnReload.setOnClickListener {
            mViewModel.search()
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                searchResultAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            emptyStatus.observe(viewLifecycleOwner, Observer {
                emptyView.isVisible = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> searchResultAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> searchResultAdapter.loadMoreFail()
                    LoadMoreStatus.END -> searchResultAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner) {
            mViewModel.updateListCollectState()
        }
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATED, viewLifecycleOwner) {
            mViewModel.updateItemCollectState(it)
        }
    }

    fun doSearch(searchWords: String) {
        mViewModel.search(searchWords)
    }

}
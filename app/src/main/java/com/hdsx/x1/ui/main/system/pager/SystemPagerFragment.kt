package com.hdsx.x1.ui.main.system.pager

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hdsx.R
import com.hdsx.x1.common.ScrollToTop
import com.hdsx.x1.common.loadmore.CommonLoadMoreView
import com.hdsx.x1.common.loadmore.LoadMoreStatus
import com.hdsx.x1.ext.toIntPx
import com.hdsx.x1.model.bean.Category
import com.hdsx.x1.ui.base.BaseVmFragment
import com.hdsx.x1.ui.detail.DetailActivity
import com.hdsx.x1.ui.main.home.CategoryAdapter
import com.hdsx.x1.ui.main.home.SimpleArticleAdapter
import com.hdsx.x1.util.core.ActivityManager
import com.hdsx.x1.util.core.bus.Bus
import com.hdsx.x1.util.core.bus.USER_COLLECT_UPDATED
import com.hdsx.x1.util.core.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.include_reload.*
import kotlinx.android.synthetic.main.fragment_system_pager.*

class SystemPagerFragment : BaseVmFragment<SystemPagerViewModel>(), ScrollToTop {

    companion object {

        private const val CATEGORY_LIST = "CATEGORY_LIST"

        fun newInstance(categoryList: ArrayList<Category>): SystemPagerFragment {
            return SystemPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(CATEGORY_LIST, categoryList)
                }
            }
        }
    }

    private lateinit var categoryList: List<Category>
    var checkedPosition = 0
    private lateinit var mAdapterSimple: SimpleArticleAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun layoutRes() = R.layout.fragment_system_pager

    override fun viewModelClass() = SystemPagerViewModel::class.java

    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                mViewModel.refreshArticleList(categoryList[checkedPosition].id)
            }
        }

        categoryList = arguments?.getParcelableArrayList(CATEGORY_LIST)!!
        checkedPosition = 0
        categoryAdapter = CategoryAdapter(R.layout.item_category_sub).apply {
            bindToRecyclerView(rvCategory)
            setNewData(categoryList)
            onCheckedListener = {
                checkedPosition = it
                mViewModel.refreshArticleList(categoryList[checkedPosition].id)
            }
        }

        mAdapterSimple = SimpleArticleAdapter(R.layout.item_article_simple).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMoreArticleList(categoryList[checkedPosition].id)
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = mAdapterSimple.data[position]
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
                val article = mAdapterSimple.data[position]
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
        btnReload.setOnClickListener {
            mViewModel.refreshArticleList(categoryList[checkedPosition].id)
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapterSimple.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapterSimple.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapterSimple.loadMoreFail()
                    LoadMoreStatus.END -> mAdapterSimple.loadMoreEnd()
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

    override fun lazyLoadData() {
        mViewModel.refreshArticleList(categoryList[checkedPosition].id)
    }

    fun check(position: Int) {
        if (position != checkedPosition) {
            checkedPosition = position
            categoryAdapter.check(position)
            (rvCategory.layoutManager as? LinearLayoutManager)
                ?.scrollToPositionWithOffset(position, 8f.toIntPx())
            mViewModel.refreshArticleList(categoryList[checkedPosition].id)
        }
    }

    override fun scrollTop() {
        recyclerView?.smoothScrollToPosition(0)
    }
}
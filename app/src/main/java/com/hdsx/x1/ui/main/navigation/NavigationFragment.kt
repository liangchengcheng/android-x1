package com.hdsx.x1.ui.main.navigation

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hdsx.R
import com.hdsx.x1.common.ScrollToTop
import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.ui.base.BaseVmFragment
import com.hdsx.x1.ui.detail.DetailActivity
import com.hdsx.x1.ui.main.MainActivity
import com.hdsx.x1.util.core.ActivityManager
import kotlinx.android.synthetic.main.include_reload.*
import kotlinx.android.synthetic.main.fragment_navigation.*

/**
 * 导航的fragment
 */
class NavigationFragment : BaseVmFragment<NavigationViewModel>(), ScrollToTop {

    private lateinit var mAdapter: NavigationAdapter
    private var currentPosition = 0

    companion object {
        fun newInstance() = NavigationFragment()
    }

    override fun layoutRes() = R.layout.fragment_navigation

    override fun viewModelClass() = NavigationViewModel::class.java

    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.getNavigations() }
        }
        mAdapter = NavigationAdapter(R.layout.item_navigation).apply {
            bindToRecyclerView(recyclerView)
            // 设置一个函数
            onItemTagClickListener = {
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to Article(title = it.title, link = it.link))
                )
            }
        }

        btnReload.setOnClickListener {
            mViewModel.getNavigations()
        }

        recyclerView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (activity is MainActivity && scrollY != oldScrollY) {
                (activity as MainActivity).animateBottomNavigationView(scrollY < oldScrollY)
            }
            if (scrollY < oldScrollY) {
                tvFloatTitle.text = mAdapter.data[currentPosition].name
            }
            val lm = recyclerView.layoutManager as LinearLayoutManager
            val nextView = lm.findViewByPosition(currentPosition + 1)
            if (nextView != null) {
                tvFloatTitle.y = if (nextView.top < tvFloatTitle.measuredHeight) {
                    (nextView.top - tvFloatTitle.measuredHeight).toFloat()
                } else {
                    0f
                }
            }
            currentPosition = lm.findFirstVisibleItemPosition()
            if (scrollY > oldScrollY) {
                tvFloatTitle.text = mAdapter.data[currentPosition].name
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            navigations.observe(viewLifecycleOwner, Observer {
                tvFloatTitle.isGone = it.isEmpty()
                tvFloatTitle.text = it[0].name
                mAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
    }

    override fun initData() {
        mViewModel.getNavigations()
    }

    override fun scrollTop() {
        recyclerView?.smoothScrollToPosition(0)
    }
}
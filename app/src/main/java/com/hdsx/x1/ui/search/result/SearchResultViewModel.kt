package com.hdsx.x1.ui.search.result

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.common.loadmore.LoadMoreStatus
import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.ui.base.BaseViewModel
import com.hdsx.x1.ui.common.CollectRepository
import com.hdsx.x1.util.core.bus.Bus
import com.hdsx.x1.util.core.bus.USER_COLLECT_UPDATED

class SearchResultViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    private val searchResultRepository by lazy { SearchResultRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val articleList = MutableLiveData<MutableList<Article>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()

    private var currentKeywords = ""
    private var page = INITIAL_PAGE

    /**
     * 开始搜索
     */
    fun search(keywords: String = currentKeywords) {
        if (currentKeywords != keywords) {
            currentKeywords = keywords
            articleList.value = emptyList<Article>().toMutableList()
        }

        refreshStatus.value = true
        emptyStatus.value = false
        reloadStatus.value = false

        launch(
            block = {
                val pagination = searchResultRepository.search(keywords, INITIAL_PAGE)
                page = pagination.curPage
                // 获取返回的集合
                articleList.value = pagination.datas.toMutableList()
                // 当前不刷新
                refreshStatus.value = false
                // 返回结果是否为空
                emptyStatus.value = pagination.datas.isEmpty()
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    /**
     * 加载更多
     */
    fun loadMore() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = searchResultRepository.search(currentKeywords, page)
                page = pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                // 把新数据加到集合
                currentList.addAll(pagination.datas)
                articleList.value = currentList
                // 大于或者等于总数就算加载完毕，否则就是加载完成
                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }

    fun collect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (!collectIds.contains(id)) collectIds.add(id)
                })
                updateItemCollectState(id to true)
                Bus.post(USER_COLLECT_UPDATED, id to true)
            },
            error = {
                updateItemCollectState(id to false)
            }
        )
    }

    fun uncollect(id: Int) {
        launch(
            block = {
                collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                updateItemCollectState(id to false)
                Bus.post(USER_COLLECT_UPDATED, id to false)
            },
            error = {
                updateItemCollectState(id to true)
            }
        )
    }

    fun updateListCollectState() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (userRepository.isLogin()) {
            val collectIds = userRepository.getUserInfo()?.collectIds ?: return
            list.forEach { it.collect = collectIds.contains(it.id) }
        } else {
            list.forEach { it.collect = false }
        }
        articleList.value = list
    }

    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }
}
package com.hdsx.x1.ui.collection

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.common.loadmore.LoadMoreStatus
import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.ui.base.BaseViewModel
import com.hdsx.x1.ui.common.CollectRepository
import com.hdsx.x1.util.core.bus.Bus
import com.hdsx.x1.util.core.bus.USER_COLLECT_UPDATED

class CollectionViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0;
    }

    /**
     * 网络请求
     */
    private val collectionRepository by lazy {
        CollectionRepository()
    }

    /**
     * 本地的缓存
     */
    private val collectRepository by lazy { CollectRepository() }

    val articleList = MutableLiveData<MutableList<Article>>()
    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()
    private var page = INITIAL_PAGE

    /**
     * 刷新
     */
    fun refresh() {
        refreshStatus.value = true
        emptyStatus.value = false
        reloadStatus.value = false
        launch(
            block = {
                // 获取第一页的数据
                val pagination = collectionRepository.getCollectionList(INITIAL_PAGE)
                // 设置状态为已收藏
                pagination.datas.forEach{
                    it.collect = true
                }

                // 设置当前页码
                page = pagination.curPage
                articleList.value = pagination.datas.toMutableList()
                emptyStatus.value = pagination.datas.isEmpty()
                refreshStatus.value = false
            },
            error = {
                reloadStatus.value = page == INITIAL_PAGE
                refreshStatus.value = false
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
                val pagination = collectionRepository.getCollectionList(page)
                pagination.datas.forEach { it.collect = true }
                page = pagination.curPage

                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)

                articleList.value = currentList
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

    /**
     * 取消收藏
     */
    fun uncollect(id: Int) {
        launch(
            block = {
                collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                Bus.post(USER_COLLECT_UPDATED, id to false)
            }
        )
    }

}
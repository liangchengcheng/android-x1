package com.hdsx.x1.ui.history

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.ui.base.BaseViewModel
import com.hdsx.x1.ui.common.CollectRepository
import com.hdsx.x1.util.core.bus.Bus
import com.hdsx.x1.util.core.bus.USER_COLLECT_UPDATED

class HistoryViewModel : BaseViewModel() {

    private val historyRepository by lazy { HistoryRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val articleList = MutableLiveData<MutableList<Article>>()
    val emptyStatus = MutableLiveData<Boolean>()

    /**
     * 获取历史记录列表
     */
    fun getData() {
        emptyStatus.value = false
        launch(
            block = {
                // 获取全部的历史记录
                val readHistory = historyRepository.getReadHistory()
                // 获取登录返回时收藏 ids
                val collectIds = userRepository.getUserInfo()?.collectIds ?: emptyList<Int>()
                // 更新收藏的状态(看看是否包含)
                readHistory.forEach {
                    it.collect = collectIds.contains(it.id)
                }

                articleList.value = readHistory.toMutableList()
                emptyStatus.value = readHistory.isEmpty()
            }
        )
    }

    /**
     * 收藏
     */
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
                updateItemCollectState(id to false)
                Bus.post(USER_COLLECT_UPDATED, id to false)
            },
            error = {
                updateItemCollectState(id to true)
            }
        )
    }


    /**
     * 登录信息改变后，重新读取用户信息和收藏状态
     */
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

    /**
     * 更新条目的收藏状态
     */
    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find {
            it.id == target.first
        } ?: return
        item.collect = target.second
        articleList.value = list
    }

    /**
     * 删除历史记录
     */
    fun deleteHistory(article: Article) {
        launch(
            block = { historyRepository.deleteHistory(article) }
        )
    }
}
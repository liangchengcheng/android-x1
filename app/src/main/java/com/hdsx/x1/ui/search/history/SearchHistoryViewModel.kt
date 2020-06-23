package com.hdsx.x1.ui.search.history

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.model.bean.HotWord
import com.hdsx.x1.ui.base.BaseViewModel

class SearchHistoryViewModel : BaseViewModel() {

    private val searchHistoryRepository by lazy { SearchHistoryRepository() }

    /**
     * List 应该不是不可观察集合类型
     */
    val hotWords = MutableLiveData<List<HotWord>>()

    /**
     * 历史记录
     */
    val searchHistory = MutableLiveData<MutableList<String>>()


    /**
     * 获取热门搜索
     */
    fun getHotSearch() {
        launch(block = { hotWords.value = searchHistoryRepository.getHotSearch() })
    }

    /**
     * 获取搜索历史记录
     */
    fun getSearchHistory() {
        searchHistory.value = searchHistoryRepository.getSearchHisory()
    }

    /**
     * 添加历史记录
     */
    fun addSearchHistory(searchWords: String) {
        // 看看搜索历史记录是否为空？要不初始化一个新的
        val history = searchHistory.value ?: mutableListOf()
        // 要是原来的就包含先移除原来的
        if (history.contains(searchWords)) {
            history.remove(searchWords)
        }

        // 然后在头部添加一个新的
        history.add(0, searchWords)
        searchHistory.value = history
        // 保存历史记录
        searchHistoryRepository.saveSearchHistory(searchWords)
    }

    /**
     * 删除历史记录
     */
    fun deleteSearchHistory(searchWords: String) {
        val history = searchHistory.value ?: mutableListOf()
        if (history.contains(searchWords)) {
            history.remove(searchWords)
            searchHistory.value = history
            searchHistoryRepository.deleteSearchHistory(searchWords)
        }
    }


}
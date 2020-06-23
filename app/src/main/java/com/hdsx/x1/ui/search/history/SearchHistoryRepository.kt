package com.hdsx.x1.ui.search.history

import com.hdsx.x1.model.api.RetrofitClient
import com.hdsx.x1.model.store.SearchHistoryStore

class SearchHistoryRepository {

    suspend fun getHotSearch() = RetrofitClient.apiService.getHotWords().apiData()

    fun saveSearchHistory(searchWords: String) {
        SearchHistoryStore.saveSearchHistory(searchWords)
    }

    fun deleteSearchHistory(searchWords: String) {
        SearchHistoryStore.deleteSearchHistory(searchWords)
    }

    fun getSearchHisory() = SearchHistoryStore.getSearchHistory()

}
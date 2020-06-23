package com.hdsx.x1.ui.search.result

import com.hdsx.x1.model.api.RetrofitClient

class SearchResultRepository {
    /**
     * 搜索结果
     */
    suspend fun search(keywords: String, page: Int) =
        RetrofitClient.apiService.search(keywords, page).apiData()
}
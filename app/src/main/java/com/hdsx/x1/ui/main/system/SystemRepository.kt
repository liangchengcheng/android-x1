package com.hdsx.x1.ui.main.system

import com.hdsx.x1.model.api.RetrofitClient

class SystemRepository {
    suspend fun getArticleCategories() =
        RetrofitClient.apiService.getArticleCategories().apiData()
}
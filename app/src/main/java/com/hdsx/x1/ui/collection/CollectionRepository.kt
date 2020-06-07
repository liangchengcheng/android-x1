package com.hdsx.x1.ui.collection

import com.hdsx.x1.model.api.RetrofitClient

class CollectionRepository {
    suspend fun getCollectionList(page: Int) =
        RetrofitClient.apiService.getCollectionList(page).apiData()
}
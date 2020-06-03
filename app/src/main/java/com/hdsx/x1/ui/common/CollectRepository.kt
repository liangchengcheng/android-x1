package com.hdsx.x1.ui.common

import com.hdsx.x1.model.api.RetrofitClient

class CollectRepository {
    /**
     * 收藏和取消收藏- 采用协程
     */
    suspend fun collect(id: Int) = RetrofitClient.apiService.collect(id).apiData()
    suspend fun uncollect(id: Int) = RetrofitClient.apiService.uncollect(id).apiData()

}
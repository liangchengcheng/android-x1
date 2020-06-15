package com.hdsx.x1.ui.main.system.pager

import com.hdsx.x1.model.api.RetrofitClient

class SystemPagerRepository {
    suspend fun getArticleListByCid(page: Int, cid: Int) =
        RetrofitClient.apiService.getArticleListByCid(page, cid).apiData()
}
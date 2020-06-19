package com.hdsx.x1.ui.main.discovery

import com.hdsx.x1.model.api.RetrofitClient

class DiscoveryRepository {
    /**
     * 获取广告的集合
     */
    suspend fun getBanners() = RetrofitClient.apiService.getBanners().apiData()

    /**
     * 获取热词的集合
     */
    suspend fun getHotWords() = RetrofitClient.apiService.getHotWords().apiData()

    /**
     * 获取热门站点
     */
    suspend fun getFrequentlyWebsites() =
        RetrofitClient.apiService.getFrequentlyWebsites().apiData()
}
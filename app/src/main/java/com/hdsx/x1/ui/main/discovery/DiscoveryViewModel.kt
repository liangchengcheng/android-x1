package com.hdsx.x1.ui.main.discovery

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.model.bean.Banner
import com.hdsx.x1.model.bean.Frequently
import com.hdsx.x1.model.bean.HotWord
import com.hdsx.x1.ui.base.BaseViewModel

class DiscoveryViewModel : BaseViewModel() {

    private val dicoveryRepository by lazy {
        DiscoveryRepository()
    }

    val banners = MutableLiveData<List<Banner>>()
    val hotWords = MutableLiveData<List<HotWord>>()
    val frequentlyList = MutableLiveData<List<Frequently>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    fun getData() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                banners.value = dicoveryRepository.getBanners()
                hotWords.value = dicoveryRepository.getHotWords()
                frequentlyList.value = dicoveryRepository.getFrequentlyWebsites()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

}

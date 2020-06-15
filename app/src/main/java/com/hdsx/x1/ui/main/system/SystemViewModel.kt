package com.hdsx.x1.ui.main.system

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.model.bean.Category
import com.hdsx.x1.ui.base.BaseViewModel

class SystemViewModel : BaseViewModel() {

    private val systemRepository by lazy { SystemRepository() }
    val categories: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val loadingStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()


    fun getArticleCategory() {
        loadingStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                categories.value = systemRepository.getArticleCategories()
                loadingStatus.value = false
            },
            error = {
                loadingStatus.value = false
                reloadStatus.value = true
            }
        )
    }

}
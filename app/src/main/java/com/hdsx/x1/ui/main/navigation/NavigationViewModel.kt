package com.hdsx.x1.ui.main.navigation

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.model.bean.Navigation
import com.hdsx.x1.ui.base.BaseViewModel

class NavigationViewModel : BaseViewModel() {

    private val navigationRepository by lazy { NavigationRepository() }

    val navigations = MutableLiveData<List<Navigation>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    fun getNavigations() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                navigations.value = navigationRepository.getNavigations()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = navigations.value.isNullOrEmpty()
            }
        )
    }
}
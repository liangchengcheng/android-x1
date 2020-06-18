package com.hdsx.x1.ui.main.navigation

import com.hdsx.x1.model.api.RetrofitClient

class NavigationRepository {

    suspend fun getNavigations() = RetrofitClient.apiService.getNavigations().apiData()

}
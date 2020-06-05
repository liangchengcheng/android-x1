package com.hdsx.x1.ui.register

import com.hdsx.x1.model.api.RetrofitClient

class RegisterRepository {

    suspend fun register(username: String, password: String, repassword: String) =
        RetrofitClient.apiService.register(username, password, repassword).apiData()
}
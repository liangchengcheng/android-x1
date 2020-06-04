package com.hdsx.x1.ui.login

import com.hdsx.x1.model.api.RetrofitClient

class LoginRepository {
    /**
     * 用户名和密码登录
     */
    suspend fun login(username: String, password: String) =
        RetrofitClient.apiService.login(username, password).apiData()

}
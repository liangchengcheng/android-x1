package com.hdsx.x1.ui.common

import com.hdsx.x1.model.api.RetrofitClient
import com.hdsx.x1.model.bean.UserInfo
import com.hdsx.x1.model.store.UserInfoStore

class UserRepository {
    fun updateUserInfo(userInfo: UserInfo) = UserInfoStore.setUserInfo(userInfo)

    fun isLogin() = UserInfoStore.isLogin()

    fun getUserInfo() = UserInfoStore.getUserInfo()

    fun clearLoginState() {
        UserInfoStore.clearUserInfo()
        RetrofitClient.clearCookie()
    }

}
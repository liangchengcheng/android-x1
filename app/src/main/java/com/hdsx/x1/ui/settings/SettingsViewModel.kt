package com.hdsx.x1.ui.settings

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.ui.base.BaseViewModel
import com.hdsx.x1.util.core.bus.Bus
import com.hdsx.x1.util.core.bus.USER_LOGIN_STATE_CHANGED


class SettingsViewModel : BaseViewModel() {

    val isLogin = MutableLiveData<Boolean>()

    fun getLoginStatus() {
        isLogin.value = userRepository.isLogin()
    }

    fun logout() {
        userRepository.clearLoginState()
        Bus.post(USER_LOGIN_STATE_CHANGED, false)
    }
}
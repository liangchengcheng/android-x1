package com.hdsx.x1.ui.register

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.ui.base.BaseViewModel
import com.hdsx.x1.util.core.bus.Bus
import com.hdsx.x1.util.core.bus.USER_LOGIN_STATE_CHANGED

class RegisterViewModel : BaseViewModel() {
    private val registerRepository by lazy { RegisterRepository() }

    val submitting = MutableLiveData<Boolean>()

    val registerResult = MutableLiveData<Boolean>()

    fun register(account: String, password: String, confirmPassword: String) {
        submitting.value = true
        launch(
            block = {
                // 注册用户并返回用户的信息
                val userInfo = registerRepository.register(account, password, confirmPassword)
                // 更新本地的用户信息
                userRepository.updateUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED, true)
                registerResult.value = true
                submitting.value = true
            },
            error = {
                registerResult.value = false
                submitting.value = false
            }
        )
    }
}
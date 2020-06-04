package com.hdsx.x1.ui.login

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.ui.base.BaseViewModel
import com.hdsx.x1.util.core.bus.Bus
import com.hdsx.x1.util.core.bus.USER_LOGIN_STATE_CHANGED

class LoginViewModel : BaseViewModel() {

    private val loginRepository by lazy { LoginRepository() }

    /**
     * 是否在提交登录请求
     */
    val submitting = MutableLiveData<Boolean>()

    /**
     * 是否有登录成功返回的结果
     */
    val loginResult = MutableLiveData<Boolean>()


    fun login(account: String, password: String) {
        submitting.value = true

        launch(
            block = {
                val userInfo = loginRepository.login(account, password)
                userRepository.updateUserInfo(userInfo)

                Bus.post(USER_LOGIN_STATE_CHANGED, true)
                submitting.value = false
                loginResult.value = true
            },
            error = {
                submitting.value = false
                loginResult.value = false
            }
        )
    }
}
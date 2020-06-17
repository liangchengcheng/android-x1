package com.hdsx.x1.ui.main.mine

import androidx.lifecycle.MutableLiveData
import com.hdsx.x1.model.bean.UserInfo
import com.hdsx.x1.ui.base.BaseViewModel

class MineViewModel : BaseViewModel() {

    private val mineRespository by lazy { MineRespository() }

    val userInfo = MutableLiveData<UserInfo?>()
    val isLogin = MutableLiveData<Boolean>()

    fun getUserInfo() {
        isLogin.value = userRepository.isLogin()
        userInfo.value = userRepository.getUserInfo()
    }
}
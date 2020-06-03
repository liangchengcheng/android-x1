package com.hdsx.x1.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.hdsx.R
import com.hdsx.x1.App
import com.hdsx.x1.ext.showToast
import com.hdsx.x1.model.api.ApiException
import com.hdsx.x1.ui.common.UserRepository
import com.hdsx.x1.util.core.bus.Bus
import com.hdsx.x1.util.core.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.SocketTimeoutException

typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit


open class BaseViewModel : ViewModel() {
    protected val userRepository by lazy { UserRepository() }

    val loginStatusInvalid: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @return Job
     */
    protected fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
        return viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     */
    private fun onError(e: Exception) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        // 登录失效
                        userRepository.clearLoginState()
                        Bus.post(USER_LOGIN_STATE_CHANGED, false)
                        loginStatusInvalid.value = true
                    }
                    -1 -> {
                        // 其他api错误
                        App.instance.showToast(e.message)
                    }
                    else -> {
                        // 其他错误
                        App.instance.showToast(e.message)
                    }
                }
            }
            is ConnectException -> {
                // 连接失败
                App.instance.showToast(App.instance.getString(R.string.network_connection_failed))
            }
            is SocketTimeoutException -> {
                // 请求超时
                App.instance.showToast(App.instance.getString(R.string.network_request_timeout))
            }
            is JsonParseException -> {
                // 数据解析错误
                App.instance.showToast(App.instance.getString(R.string.api_data_parse_error))
            }
            else -> {
                // 其他错误
                e.message?.let { App.instance.showToast(it) }
            }
        }
    }


    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }

    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 登录状态
     */
    fun loginStatus() = userRepository.isLogin()

}
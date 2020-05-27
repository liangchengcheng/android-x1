package com.hdsx.x1

import android.app.Application
import com.hdsx.x1.util.isMainProcess

class App: Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        // 主进程初始化
        if (isMainProcess(this)) {
            init()
        }
    }

    private fun init() {

    }

    private fun rigesterActivityCallbacks() {

    }
}
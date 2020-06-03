package com.hdsx.x1

import android.app.Application
import com.hdsx.x1.common.ActivityLifecycleCallbacksAdapter
import com.hdsx.x1.model.store.SettingsStore
import com.hdsx.x1.util.core.ActivityManager
import com.hdsx.x1.util.isMainProcess
import com.hdsx.x1.util.setNightMode

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
        registerActivityCallbacks()
        setDayNightMode()
    }

    private fun setDayNightMode() {
        setNightMode(SettingsStore.getNightMode())
    }

    private fun registerActivityCallbacks() {
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksAdapter(
            onActivityCreated = { activity, _ ->
                ActivityManager.activities.add(activity)
            },
            onActivityDestroyed = { activity ->
                ActivityManager.activities.remove(activity)
            }
        ))
    }
}
package com.hdsx.x1.util.core

import android.app.Activity
import android.content.Intent
import com.hdsx.x1.ext.putExtras

/**
 *  object class 是已经初始化好的类， 且仅有一份
 */
object ActivityManager {

    val activities = mutableListOf<Activity>()

    fun start(clazz: Class<out Activity>, params: Map<String, Any> = emptyMap()) {
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
    }

    /**
     * finish指定的一个或多个Activity
     */
    fun finish(vararg clazz: Class<out Activity>) {
        activities.forEach { activiy ->
            if (clazz.contains(activiy::class.java)) {
                activiy.finish()
            }
        }
    }
}
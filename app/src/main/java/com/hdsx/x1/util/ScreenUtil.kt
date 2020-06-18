package com.hdsx.x1.util

import android.content.Context

fun getSreenHeight(context: Context) = context.resources.displayMetrics.heightPixels

fun getSreenWidth(context: Context) = context.resources.displayMetrics.widthPixels

/**
 * 获取状态栏的高度
 */
fun getStatusBarHeight(context: Context) : Int {
    var statusBarHeight = 0
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

    if (resourceId > 0) {
        statusBarHeight = context.resources.getDimensionPixelOffset(resourceId)
    }

    return statusBarHeight
}
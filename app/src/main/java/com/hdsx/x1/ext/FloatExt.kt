package com.hdsx.x1.ext

import com.hdsx.x1.App
import com.hdsx.x1.util.dpToPx
import com.hdsx.x1.util.pxToDp


fun Float.toPx() = dpToPx(App.instance, this)

fun Float.toIntPx() = dpToPx(App.instance, this).toInt()

fun Float.toDp() = pxToDp(App.instance, this)

fun Float.toIntDp() = pxToDp(App.instance, this).toInt()
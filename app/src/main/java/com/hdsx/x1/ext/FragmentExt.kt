package com.hdsx.x1.ext

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

/**
 * fragment的跳转事件
 */
fun Fragment.openInExplorer(link: String?) {
    startActivity(Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(link)
    })
}
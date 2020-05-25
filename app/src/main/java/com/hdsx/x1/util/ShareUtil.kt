package com.hdsx.x1.util

import android.app.Activity
import androidx.core.app.ShareCompat

fun share(activity: Activity, title: String? = "wan android", content: String?) {
    ShareCompat.IntentBuilder.from(activity)
        .setType("text/plain")
        .setSubject(title)
        .setText(content)
        .setChooserTitle(title)
        .startChooser()
}
package com.hdsx.x1.ui.splash

import android.os.Bundle
import com.hdsx.R
import com.hdsx.x1.MainActivity
import com.hdsx.x1.ui.base.BaseActivity
import com.hdsx.x1.util.core.ActivityManager

class SplashActivity : BaseActivity() {

    override fun layoutRes() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.postDelayed({
            ActivityManager.start(MainActivity::class.java)
            ActivityManager.finish(SplashActivity::class.java)
        }, 1000)
    }
}
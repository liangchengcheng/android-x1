package com.hdsx.x1.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import com.google.android.material.animation.AnimationUtils
import com.hdsx.x1.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var fragments: Map<Int, Fragment>
    private var bottomNavigationViewAnimtor: ViewPropertyAnimator? = null
    private var currentBottomNavagtionState = true
    private var previousTimeMillis = 0L

    fun animateBottomNavigationView(show: Boolean) {

    }
}
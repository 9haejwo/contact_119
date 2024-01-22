package com.android.contact_119.extensions

import android.view.View
import android.view.animation.AnimationUtils
import com.android.contact_119.R
import com.android.contact_119.fragment.gone
import com.android.contact_119.fragment.visible

fun View.setVisibleFadeIn() {
    val fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in)

    startAnimation(fadeInAnim)
    visibility = visible
}

fun View.setGoneFadeOut() {
    val fadeOutAnim = AnimationUtils.loadAnimation(context, R.anim.fade_out)

    startAnimation(fadeOutAnim)
    visibility = gone
}
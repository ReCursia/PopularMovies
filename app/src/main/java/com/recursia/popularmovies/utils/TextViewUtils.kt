package com.recursia.popularmovies.utils

import android.animation.ObjectAnimator
import android.widget.TextView

object TextViewUtils {

    fun expandTextViewWithAnimation(tv: TextView, duration: Long) {
        val animation = ObjectAnimator.ofInt(tv, "maxLines", tv.lineCount)
        animation.setDuration(duration).start()
    }

    fun collapseTextView(tv: TextView, lineLimit: Int) {
        tv.maxLines = lineLimit
    }
}

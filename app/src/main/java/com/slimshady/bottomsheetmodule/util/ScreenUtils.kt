package com.slimshady.bottomsheetmodule.util

import android.util.DisplayMetrics
import android.app.Activity
import android.content.Context

object ScreenUtils {
    @JvmStatic
    fun getWindowHeight(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}
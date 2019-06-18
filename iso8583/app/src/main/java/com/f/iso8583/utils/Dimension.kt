package com.f.iso8583.utils

import android.content.Context
import android.util.DisplayMetrics

object Dimension {
    fun convertDpToPixel(context: Context, dp:Float):Float{
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
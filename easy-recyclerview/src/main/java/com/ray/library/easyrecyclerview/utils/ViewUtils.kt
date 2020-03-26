package com.ray.library.easyrecyclerview.utils

import android.content.Context

/**
 * @author Ray Huang
 * @since 2020/3/25
 */
open class ViewUtils {

  companion object Functions {

    /**
     * Returns the screen density
     *
     *
     * 120dpi = 0.75
     * 160dpi = 1 (default)
     * 240dpi = 1.5
     * 320dpi = 2
     * 400dpi = 2.5
     *
     * @param context application context
     * @return Device density
     */
    fun getDensity(context: Context): Float {
      val metrics = context.resources.displayMetrics
      return metrics.density
    }

    /**
     * Coverts px to dp.
     *
     * @param px pixel
     * @param context application context
     * @return dp
     */
    fun convertPixel2Dp(
      px: Float,
      context: Context
    ): Int {
      return (px / getDensity(context) + 0.5f).toInt()
    }

    /**
     * Coverts dp to px.
     *
     * @param dp dp
     * @param context application context
     * @return pixel
     */
    fun convertDp2Pixel(
      dp: Float,
      context: Context
    ): Int {
      return (dp * getDensity(context) + 0.5f).toInt()
    }
  }
}
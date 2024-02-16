package com.firstapp.weatherapp.utils

import android.app.Activity
import com.firstapp.weatherapp.R

class Functions {
    companion object {

        /**
         * @param desc the weather description
         * @return the drawable resource id
         */
        fun getWeatherIconFromDesc(desc: String): Int {
            val lower = desc.lowercase()
            return if (lower.contains("rain") || lower.contains("drizzle"))
                R.drawable.rainy
            else if (lower.contains("snow"))
                R.drawable.snowy
            else if (
                (lower.contains("partly") || lower.contains("partially")) &&
                lower.contains("cloud")
            )
                R.drawable.cloudy_sunny
            else if (
                lower.contains("cloud") ||
                lower.contains("mist") ||
                lower.contains("overcast")
            )
                R.drawable.cloudy
            else if (lower.contains("sunny"))
                R.drawable.sunny
            else if (lower.contains("sleet") || lower.contains("ice"))
                R.drawable.storm
            else R.drawable.cloudy
        }

        fun getWeatherBackgroundFromDesc(desc: String): Int {
            val lower = desc.lowercase()
            return if (lower.contains("rain") || lower.contains("drizzle"))
                R.drawable.rainy_bg
            else if (lower.contains("snow"))
                R.drawable.snow_bg
            else if (
                (lower.contains("partly") || lower.contains("partially")) &&
                lower.contains("cloud")
            )
                R.drawable.cloudy_bg
            else if (
                lower.contains("cloud") ||
                lower.contains("mist") ||
                lower.contains("overcast")
            )
                R.drawable.haze_bg
            else if (lower.contains("sunny"))
                R.drawable.sunny_bg
            else if (lower.contains("sleet") || lower.contains("ice"))
                R.drawable.rainy_bg
            else R.drawable.cloudy_bg
        }

        fun changeStatusBarColor(activity: Activity, background: Int) {
            activity.window.statusBarColor = activity.getColor(
                when (background) {
                    R.drawable.sunny_bg -> R.color.sunny_status_bar
                    R.drawable.cloudy_bg -> R.color.cloudy_status_bar
                    R.drawable.rainy_bg -> R.color.rainy_status_bar
                    R.drawable.snow_bg -> R.color.snowy_status_bar
                    R.drawable.haze_bg -> R.color.haze_status_bar
                    else -> R.color.status_bar
                }
            )
        }
    }

}
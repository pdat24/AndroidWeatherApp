package com.firstapp.weatherapp.models

data class Current(
    val cloud: Int,
    val condition: Condition,
    val feelslike_c: Float,
    val feelslike_f: Float,
    val gust_kph: Float,
    val gust_mph: Float,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Long,
    val precip_in: Float,
    val precip_mm: Float,
    val pressure_in: Float,
    val pressure_mb: Float,
    val temp_c: Float,
    val temp_f: Float,
    val uv: Float,
    val vis_km: Float,
    val vis_miles: Float,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Float,
    val wind_mph: Float
)
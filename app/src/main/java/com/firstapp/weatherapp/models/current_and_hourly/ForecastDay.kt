package com.firstapp.weatherapp.models.current_and_hourly

data class ForecastDay(
    val astro: Astro,
    val date: String,
    val date_epoch: Long,
    val day: Day,
    val hour: List<HourlyWeather>
)
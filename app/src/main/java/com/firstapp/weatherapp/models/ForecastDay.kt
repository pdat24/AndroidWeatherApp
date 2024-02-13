package com.firstapp.weatherapp.models

data class ForecastDay(
    val astro: Astro,
    val date: String,
    val date_epoch: Long,
    val day: Day,
    val hour: List<ForecastHour>
)
package com.firstapp.weatherapp.models

data class HourlyForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Weather>,
    val message: Int
)
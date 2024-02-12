package com.firstapp.weatherapp.models

data class HourlyWeather(
    val clouds: Clouds,
    val dt: Long,
    val dt_txt: String,
    val main: HourlyWeatherMain,
    val pop: Double,
    val rain: Rain,
    val sys: HourlyWeatherSys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
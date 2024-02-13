package com.firstapp.weatherapp.models

data class WeatherForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)
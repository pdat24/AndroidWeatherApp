package com.firstapp.weatherapp.models.current_and_hourly

data class WeatherForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)
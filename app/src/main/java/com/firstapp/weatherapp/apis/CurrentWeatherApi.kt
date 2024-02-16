package com.firstapp.weatherapp.apis

import com.firstapp.weatherapp.models.current_and_hourly.CurrentWeather
import com.firstapp.weatherapp.models.current_and_hourly.WeatherForecast
import com.firstapp.weatherapp.utils.Constants.Companion.CURRENT_WEATHER_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherApi {
    @GET("current.json?api=no&key=${CURRENT_WEATHER_API_KEY}")
    suspend fun getCurrentWeather(
        @Query("q") latAndLon: String,
    ): Response<CurrentWeather>

    @GET("forecast.json?key=${CURRENT_WEATHER_API_KEY}&days=1&aqi=no&alerts=no")
    suspend fun getWeatherForecastToday(
        @Query("q") latAndLon: String,
    ): Response<WeatherForecast>
}
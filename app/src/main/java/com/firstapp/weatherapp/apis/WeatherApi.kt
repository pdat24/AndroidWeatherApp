package com.firstapp.weatherapp.apis

import com.firstapp.weatherapp.models.CurrentWeather
import com.firstapp.weatherapp.models.WeatherForecast
import com.firstapp.weatherapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json?api=no&key=${API_KEY}")
    suspend fun getCurrentWeather(
        @Query("q") latAndLon: String,
    ): Response<CurrentWeather>

    @GET("forecast.json?key=${API_KEY}&days=1&aqi=no&alerts=no")
    suspend fun getWeatherForecastToday(
        @Query("q") latAndLon: String,
    ): Response<WeatherForecast>
}
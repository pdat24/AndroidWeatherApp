package com.firstapp.weatherapp.apis

import com.firstapp.weatherapp.models.daily.DailyWeather
import com.firstapp.weatherapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DailyForecastApi {
    @GET("{lat}%2C{long}/{startDate}/{endDate}?unitGroup=uk&include=days&key=${Constants.DAILY_WEATHER_API_KEY}&contentType=json")
    /**
     * Date formatting pattern: yyyy-MM-dd
     */
    suspend fun getDailyForecast(
        @Path("lat") lat: Double,
        @Path("long") long: Double,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String,
    ): Response<DailyWeather>
}
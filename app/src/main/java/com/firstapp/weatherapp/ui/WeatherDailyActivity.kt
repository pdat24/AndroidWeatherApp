package com.firstapp.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firstapp.weatherapp.R
import com.firstapp.weatherapp.adapters.DailyWeatherAdapter
import com.firstapp.weatherapp.models.daily.Day
import com.firstapp.weatherapp.utils.Functions
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.math.roundToInt

class WeatherDailyActivity : AppCompatActivity() {

    private val rcvDailyWeather: RecyclerView by lazy {
        findViewById(R.id.rcvDailyWeather)
    }
    private val loading: CircularProgressIndicator by lazy {
        findViewById(R.id.loading)
    }
    private val weatherTextDesc: TextView by lazy {
        findViewById(R.id.tvWeatherTextDesc)
    }
    private val container: View by lazy {
        findViewById(R.id.container)
    }
    private val weatherImageDesc: ImageView by lazy {
        findViewById(R.id.ivWeatherImageDesc)
    }
    private val city: TextView by lazy {
        findViewById(R.id.tvCity)
    }
    private val tempMaxMin: TextView by lazy {
        findViewById(R.id.tvTempMaxMin)
    }
    private val temperature: TextView by lazy {
        findViewById(R.id.tvTemperature)
    }
    private val visibility: TextView by lazy {
        findViewById(R.id.tvVisibility)
    }
    private val windSpeed: TextView by lazy {
        findViewById(R.id.tvWindSpeed)
    }
    private val humidity: TextView by lazy {
        findViewById(R.id.tvHumidity)
    }
    private val weekday: TextView by lazy {
        findViewById(R.id.tvWeekday)
    }
    private val showingDay = MutableLiveData<Day>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_daily)
        rcvDailyWeather.layoutManager = LinearLayoutManager(this)
        observeShowingDayChange()
        renderDailyWeather()
    }

    private fun renderDailyWeather() {
        MainActivity.dailyWeather.observe(this) {
            loading.visibility = View.GONE
            showingDay.postValue(it.days.first())
            rcvDailyWeather.adapter = DailyWeatherAdapter(this, showingDay, it.days)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeShowingDayChange() {
        showingDay.observe(this) {
            val tmp = it.datetime.split('-')
            val date = LocalDate.of(tmp[0].toInt(), tmp[1].toInt(), tmp[2].toInt())
            weekday.text = if (
                date.minusDays(1).isEqual(LocalDate.now())
            ) "Tomorrow" else getWeekday(it.datetime)
            temperature.text = "${it.temp.roundToInt()}°"
            city.text = MainActivity.locationName
            weatherTextDesc.text = it.conditions
            val background = Functions.getWeatherBackgroundFromDesc(it.conditions)
            container.setBackgroundResource(background)
            Functions.changeStatusBarColor(this, background)
            weatherImageDesc.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    Functions.getWeatherIconFromDesc(it.conditions)
                )
            )
            tempMaxMin.text = "H:${it.tempmax.roundToInt()}°   L:${it.tempmin.roundToInt()}°"
            visibility.text = "${it.visibility.roundToInt()} km"
            windSpeed.text = "${it.windspeed.roundToInt()} km/h"
            humidity.text = "${it.humidity.roundToInt()}%"
        }
    }

    /**
     * @param date pattern: yyyy-MM-dd
     */
    private fun getWeekday(date: String): String {
        val tmp = date.split('-')
        return when (
            LocalDate.of(tmp[0].toInt(), tmp[1].toInt(), tmp[2].toInt()).dayOfWeek
        ) {
            DayOfWeek.MONDAY -> "Monday"
            DayOfWeek.TUESDAY -> "Tuesday"
            DayOfWeek.WEDNESDAY -> "Wednesday"
            DayOfWeek.THURSDAY -> "Thursday"
            DayOfWeek.FRIDAY -> "Friday"
            DayOfWeek.SATURDAY -> "Saturday"
            DayOfWeek.SUNDAY -> "Sunday"
            else -> ""
        }
    }

    fun back(view: View) = finish()
}
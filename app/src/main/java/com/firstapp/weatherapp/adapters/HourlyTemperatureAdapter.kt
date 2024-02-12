package com.firstapp.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.firstapp.weatherapp.R
import com.firstapp.weatherapp.models.HourlyWeather
import com.firstapp.weatherapp.utils.Constants.Companion.CLOUDS
import com.firstapp.weatherapp.utils.Constants.Companion.RAIN
import com.firstapp.weatherapp.utils.Constants.Companion.SNOW
import com.firstapp.weatherapp.utils.Constants.Companion.SUNNY
import com.firstapp.weatherapp.utils.Constants.Companion.WIND
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class HourlyTemperatureAdapter(
    private val hourlyForecast: List<HourlyWeather>
) : RecyclerView.Adapter<HourlyTemperatureAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hour: TextView = itemView.findViewById(R.id.tvHour)
        val temperatureImageDesc: ImageView = itemView.findViewById(R.id.ivTemperatureImageDesc)
        val temperature: TextView = itemView.findViewById(R.id.tvTemperature)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_houly_temperature, parent, false)
        )
    }

    override fun getItemCount(): Int = hourlyForecast.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = hourlyForecast[position]
        holder.hour.text = getHour(info.dt)
        holder.temperatureImageDesc.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                when (info.weather.first().main) {
                    RAIN -> R.drawable.rainy
                    CLOUDS -> R.drawable.cloudy
                    SNOW -> R.drawable.snowy
                    WIND -> R.drawable.wind
                    SUNNY -> R.drawable.sunny
                    else -> R.drawable.cloudy_sunny
                }
            )
        )
        holder.temperature.text = getTemperature(info.main.temp)
    }

    private fun getHour(time: Long): String {
        val hour = LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC).format(
            DateTimeFormatter.ofPattern("%H")
        )
        return if (hour.length == 2) hour else "0$hour"
    }

    /**
     * get celsius degree from kelvin deg
     * @return celsius degree
     */
    private fun getTemperature(kelvinDeg: Double): String {
        return (kelvinDeg - 272.15).roundToInt().toString()
    }
}
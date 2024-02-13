package com.firstapp.weatherapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.firstapp.weatherapp.R
import com.firstapp.weatherapp.models.ForecastHour
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class HourlyTemperatureAdapter(
    private var hourlyForecast: List<ForecastHour>
) : RecyclerView.Adapter<HourlyTemperatureAdapter.ViewHolder>() {

    private lateinit var context: Context

    init {
        hourlyForecast = getSortedHourlyForecast(hourlyForecast)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: View = itemView.findViewById(R.id.container)
        val hour: TextView = itemView.findViewById(R.id.tvHour)
        val temperatureImageDesc: ImageView = itemView.findViewById(R.id.ivTempImageDesc)
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = hourlyForecast[position]
        if (position == 0) {
            (holder.container.layoutParams as ViewGroup.MarginLayoutParams)
                .marginStart = getPxFromDp(16)
        } else {
            (holder.container.layoutParams as ViewGroup.MarginLayoutParams).marginStart = 0
        }
        holder.hour.text = getFormattedHour(info.time)
        holder.temperatureImageDesc.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                R.drawable.sunny
            )
        )
        holder.temperature.text = "${info.temp_c.roundToInt()}°"
    }

    private fun getPxFromDp(dp: Int): Int =
        dp * (context.resources.displayMetrics.densityDpi / 160)

    private fun getSortedHourlyForecast(forecasts: List<ForecastHour>): List<ForecastHour> {
        val currentHour = LocalTime.now().hour
        val passedHours = mutableListOf<ForecastHour>()
        val upcomingHours = mutableListOf<ForecastHour>()
        for (f in forecasts) {
            val forecastHour = getForecastHour(f.time)
            if (forecastHour < currentHour) {
                passedHours.add(f)
            } else if (forecastHour > currentHour) {
                upcomingHours.add(f)
            }
        }
        return upcomingHours + passedHours
    }

    // format pattern: yyyy-MM-dd HH:mm
    private fun getForecastHour(formattedTime: String): Int {
        return formattedTime.split(' ').last()
            .split(':').first().toInt()
    }

    // format pattern: yyyy-MM-dd HH:mm
    private fun getFormattedHour(formattedTime: String): String {
        val hour = getForecastHour(formattedTime)
        return if (hour < 12) "$hour am"
        else if (hour == 12) "$hour pm"
        else "${hour - 12} pm"
    }
}
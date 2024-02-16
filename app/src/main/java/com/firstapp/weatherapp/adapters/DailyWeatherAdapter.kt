package com.firstapp.weatherapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.firstapp.weatherapp.R
import com.firstapp.weatherapp.models.daily.Day
import com.firstapp.weatherapp.utils.Functions
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.math.roundToInt

class DailyWeatherAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val showingDay: MutableLiveData<Day>,
    private val days: List<Day>
) : RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weekday: TextView = itemView.findViewById(R.id.tvWeekday)
        val container: View = itemView.findViewById(R.id.container)
        val weatherImageDesc: ImageView = itemView.findViewById(R.id.ivWeatherImageDesc)
        val weatherTextDesc: TextView = itemView.findViewById(R.id.tvWeatherTextDesc)
        val tempMaxMin: TextView = itemView.findViewById(R.id.tvTempMaxMin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.view_daily_weather, parent, false)
        )
    }

    override fun getItemCount(): Int = days.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        showingDay.observe(lifecycleOwner) {
            holder.container.setBackgroundResource(
                if (day.datetime == it.datetime) {
                    R.drawable.bg_weather_daily_item
                } else {
                    android.R.color.transparent
                }
            )
        }
        holder.container.setOnClickListener {
            showingDay.postValue(day)
        }
        holder.weekday.text = getFormattedWeekday(day.datetime)
        holder.weatherTextDesc.text = day.conditions
        holder.weatherImageDesc.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                Functions.getWeatherIconFromDesc(day.conditions)
            )
        )
        holder.tempMaxMin.text = "${day.tempmax.roundToInt()}°    ${day.tempmin.roundToInt()}°"
    }

    /**
     * @param date pattern: yyyy-MM-dd
     */
    private fun getFormattedWeekday(date: String): String {
        val tmp = date.split('-')
        return when (
            LocalDate.of(tmp[0].toInt(), tmp[1].toInt(), tmp[2].toInt()).dayOfWeek
        ) {
            DayOfWeek.MONDAY -> "Mon"
            DayOfWeek.TUESDAY -> "Tue"
            DayOfWeek.WEDNESDAY -> "Wed"
            DayOfWeek.THURSDAY -> "Thu"
            DayOfWeek.FRIDAY -> "Fri"
            DayOfWeek.SATURDAY -> "Sat"
            DayOfWeek.SUNDAY -> "Sun"
            else -> ""
        }
    }
}
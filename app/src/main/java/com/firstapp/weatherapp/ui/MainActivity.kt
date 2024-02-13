package com.firstapp.weatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firstapp.weatherapp.R
import com.firstapp.weatherapp.adapters.HourlyTemperatureAdapter
import com.firstapp.weatherapp.apis.WeatherApi
import com.firstapp.weatherapp.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.vmadalin.easypermissions.EasyPermissions
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 12451
    }

    private val rcvHourlyTemp: RecyclerView by lazy {
        findViewById(R.id.rcvHourlyTemp)
    }
    private val weatherApi: WeatherApi = Retrofit.Builder()
        .baseUrl(Constants.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(WeatherApi::class.java)
    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    private val location: MutableLiveData<Location> = MutableLiveData()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val tempTextDesc: TextView by lazy {
        findViewById(R.id.tvTempTextDesc)
    }
    private val city: TextView by lazy {
        findViewById(R.id.tvCity)
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
    private val tempImageDesc: TextView by lazy {
        findViewById(R.id.ivTempImageDesc)
    }
    private val datetime: TextView by lazy {
        findViewById(R.id.tvDateTime)
    }
    private val viewNoData: View by lazy {
        findViewById(R.id.viewNoData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.status_bar)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContentView(R.layout.activity_main)
        requestLocationPermissionIfNeeded()
        rcvHourlyTemp.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    override fun onStart() {
        super.onStart()
        observeLocationChange()
    }

    override fun onResume() {
        super.onResume()
        updateLocation()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocation() {
        fusedLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener {
                location.postValue(it)
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    getString(R.string.can_not_get_location),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun requestLocationPermissionIfNeeded() {
        if (
            !EasyPermissions.hasPermissions(this, *locationPermissions)
        ) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.location_perm_req_rationale),
                LOCATION_PERMISSION_REQUEST_CODE,
                *locationPermissions
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun synchronizeUI(latitude: Double, longitude: Double) =
        lifecycleScope.launch {
            val currentWeatherRes = weatherApi.getCurrentWeather("$latitude,$longitude")
            val forecastRes = weatherApi.getWeatherForecastToday("$latitude,$longitude")
            if (currentWeatherRes.isSuccessful) {
                currentWeatherRes.body()?.also {
                    temperature.text = "${it.current.temp_c.roundToInt()}°"
                    city.text = it.location.name
                    datetime.text = getFormattedDateTime(LocalDateTime.now())
                    tempTextDesc.text = it.current.condition.text
                    visibility.text = "${it.current.vis_km.roundToInt()} km"
                    windSpeed.text = "${it.current.wind_kph.roundToInt()} km/h"
                    humidity.text = "${it.current.humidity}%"
                }
                viewNoData.visibility = View.GONE
            }
            if (forecastRes.isSuccessful) {
                forecastRes.body()?.also {
                    rcvHourlyTemp.adapter = HourlyTemperatureAdapter(it.forecast.forecastday.first().hour)
                }
            }
        }

    private fun getFormattedDateTime(datetime: LocalDateTime): String {
        val pattern = DateTimeFormatter.ofPattern("EEE  MMM dd │ hh:mm a")
        return datetime.format(pattern)
    }

    private fun observeLocationChange() {
        location.observe(this) {
            it.apply {
                synchronizeUI(latitude, longitude)
            }
        }
    }

    fun toWeatherDailyActivity(view: View) =
        startActivity(
            Intent(this, WeatherDailyActivity::class.java)
        )

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE)
            requestLocationPermissionIfNeeded()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {}
}
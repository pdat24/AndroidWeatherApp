package com.firstapp.weatherapp

import android.app.Application
import android.location.Location
import androidx.lifecycle.MutableLiveData

class MainApp : Application() {
    companion object {
        val location = MutableLiveData<Location>()
    }
}
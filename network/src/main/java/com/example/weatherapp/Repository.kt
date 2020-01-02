package com.example.weatherapp

import android.util.Log
import com.example.weatherapp.model.Weather
import com.example.weatherapp.network.WeatherService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@UseExperimental(InternalCoroutinesApi::class)
class Repository(private val weatherService: WeatherService) {
    suspend fun retrieveWeatherForecast(): List<Weather> {
        return weatherService.getUserZipcodes().asFlow()
            .map { weatherService.getWeather(it) }
            .toList()
    }
}
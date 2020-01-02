package com.example.weatherapp.network

import com.example.weatherapp.model.Weather

data class WeatherResponse(val city: String, val condition: String, val temperature: Int)

fun WeatherResponse.toWeather(): Weather =
    Weather(
        this.city,
        Weather.Condition.valueOf(this.condition),
        (this.temperature * (9.0 / 5.0) + 32.0).toInt()
    )
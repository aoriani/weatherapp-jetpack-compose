package com.example.weatherapp.navigation

import com.example.weatherapp.model.Weather

sealed class Screen {
    object List: Screen()
    data class Forecast(val weather: Weather): Screen()
}
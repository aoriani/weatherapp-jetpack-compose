package com.example.weatherapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.weatherapp.R

data class Weather(val city: String, val tempFahrenheit: Int, val condition: Condition) {
    enum class Condition(@StringRes val label: Int, @StringRes val description: Int, @DrawableRes val icon: Int) {
        CLOUDY(
            R.string.cloudy_weather,
            R.string.cloudy_weather_description,
            R.drawable.weather_cloudy
        ),
        RAINY(
            R.string.rainy_weather,
            R.string.rainy_weather_description,
            R.drawable.weather_pouring
        ),
        SUNNY(
            R.string.sunny_weather,
            R.string.sunny_weather_description,
            R.drawable.weather_sunny
        ),
    }
}

val sampleWeather = Weather(
    "Piracicaba",
    45,
    Weather.Condition.RAINY
)
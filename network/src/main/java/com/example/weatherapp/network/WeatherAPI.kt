package com.example.weatherapp.network

import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherAPI {

    //Damn it is broke!
    //https://issuetracker.google.com/issues/143468771
    //https://github.com/Kotlin/kotlinx.coroutines/issues/1637

    @GET("weather/zipcodes")
    suspend fun fetchUserZipCodes(): List<String>

    @GET("weather/zipcode/{zip}")
    suspend fun fetchWeather(@Path("zip") zip: String?): WeatherResponse

}
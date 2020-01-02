package com.example.weatherapp.network

import android.util.Log
import com.example.weatherapp.model.Weather
import kotlinx.coroutines.flow.asFlow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/** I hate singletons but it the simplest solution for now **/
object WeatherService {
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.i("Network", message)
            }

        }).also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()


    private val service = Retrofit.Builder().baseUrl("https://polar-waters-79301.herokuapp.com/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    suspend fun getWeather(zipCode: String): Weather {
        return service.fetchWeather(zipCode).toWeather()
    }

    suspend fun getUserZipcodes(): List<String> {
        return service.fetchUserZipCodes()
    }
}
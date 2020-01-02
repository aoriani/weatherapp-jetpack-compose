package com.example.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.memo
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import com.example.weatherapp.Repository
import com.example.weatherapp.ViewModel
import com.example.weatherapp.navigation.Navigation
import com.example.weatherapp.navigation.Screen
import com.example.weatherapp.network.WeatherService
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppContent()
            }
        }
    }

    override fun onBackPressed() {
        val hasReachedRoot = Navigation.back()
        if (hasReachedRoot) {
            finish()
        }
    }
}

@Composable
fun AppContent() {

    val viewModel = +memo { ViewModel(Repository(WeatherService)).apply { fetch() } }

    Crossfade(current = Navigation.currentScreen) { screen ->
        Surface(color = (+MaterialTheme.colors()).background) {
            when (screen) {
                is Screen.List -> ForecastList(viewModel = viewModel) { weather ->
                    Navigation.navigateTo(Screen.Forecast(weather = weather))
                }
                is Screen.Forecast -> Forecast(
                    weather = screen.weather
                )
            }
        }
    }
}

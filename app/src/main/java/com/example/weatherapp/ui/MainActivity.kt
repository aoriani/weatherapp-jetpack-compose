package com.example.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import com.example.weatherapp.navigation.Navigation
import com.example.weatherapp.navigation.Screen

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
    Crossfade(current = Navigation.currentScreen) { screen ->
        Surface(color = (+MaterialTheme.colors()).background) {
            when(screen) {
                is Screen.List -> ForecastListPreview()
                is Screen.Forecast -> Forecast(
                    weather = screen.weather
                )
            }
        }
    }
}

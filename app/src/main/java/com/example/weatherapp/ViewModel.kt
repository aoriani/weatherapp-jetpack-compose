package com.example.weatherapp

import androidx.compose.Model
import com.example.weatherapp.model.Weather
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

@Model
class ViewModel(private val repository: Repository) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private val mutex = Mutex()
    var forecast: List<Weather> = emptyList()
        private set
    var isLoading: Boolean = false
        private set

    fun init() {
        launch {
            mutex.withTryLock { //prevent concurrent attempts
                isLoading = true

                var weather: List<Weather> = emptyList()
                withContext(Dispatchers.IO) {
                    try {
                        weather = repository.retrieveWeatherForecast()
                    } catch (ignore: Exception) {

                    }
                }
                forecast = weather
                isLoading = false
            }
        }
    }

}

private suspend inline fun Mutex.withTryLock(action: () -> Unit) {
    if (tryLock()) {
        try {
            action()
        } finally {
            unlock()
        }
    }
}
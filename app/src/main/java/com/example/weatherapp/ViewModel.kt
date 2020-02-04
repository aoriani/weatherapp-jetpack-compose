package com.example.weatherapp

import androidx.compose.Model
import com.example.weatherapp.model.Weather
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

@Model
class ViewModel(private val repository: Repository) : CoroutineScope {
    enum class Status {
        Loading, Loaded, Error
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private val mutex = Mutex()
    var forecast: List<Weather> = emptyList()
        private set
    var status: Status = Status.Loading
        private set

    fun fetch() {
        launch {
            mutex.withTryLock {
                //prevent concurrent attempts
                status = Status.Loading
                withContext(Dispatchers.IO) {
                    try {
                        var weather: List<Weather> = emptyList()
                        weather = repository.retrieveWeatherForecast()
                        withContext(Dispatchers.Main) {
                            forecast = weather
                            status = Status.Loaded
                        }
                    } catch (ignore: Exception) {
                        withContext(Dispatchers.Main) {
                            status = Status.Error
                        }
                    }
                }

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
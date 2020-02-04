package com.example.weatherapp.ui

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.weatherapp.*
import com.example.weatherapp.R
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.sampleWeather
import com.example.weatherapp.navigation.Navigation

@Composable
fun Forecast(weather: Weather) {
    Column {
        TopAppBar(title = { Text(text = weather.city) }, navigationIcon = {
            VectorImageButton(id = R.drawable.ic_back) {
                Navigation.back()
            }
        })
        Container(modifier = LayoutSize.Fill, padding = EdgeInsets(16.dp)) {
            Center {
                Column {
                    VectorImage(
                        modifier = LayoutGravity.Center,
                        id = weather.condition.icon
                    )
                    Text(
                        modifier = LayoutGravity.Center,
                        text = stringResource(
                            R.string.weather_desc,
                            stringResource(weather.condition.label),
                            weather.tempFahrenheit
                        ),
                        style = MaterialTheme.typography().h3
                    )
                    Spacer(LayoutHeight(8.dp))
                    Text(
                        modifier = LayoutGravity.Center,
                        text = stringResource(weather.condition.description)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ForecastPreview() {
    Forecast(weather = sampleWeather)
}

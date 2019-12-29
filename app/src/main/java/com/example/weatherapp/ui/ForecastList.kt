package com.example.weatherapp.ui

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Modifier
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.TopAppBar
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Surface
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import com.example.weatherapp.*
import com.example.weatherapp.R
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.sampleWeather
import com.example.weatherapp.navigation.Navigation
import com.example.weatherapp.navigation.Screen

@Composable
fun ForecastListItem(item: Weather, onClick: ((Weather) -> Unit)? = null) {
    Ripple(bounded = true) {
        Clickable(onClick = { onClick?.invoke(item) }) {
            Surface(shape = RoundedCornerShape(5.dp), elevation = 5.dp, modifier = Spacing(8.dp)) {
                Row(modifier = Spacing(16.dp)) {
                    VectorImage(
                        id = item.condition.icon,
                        modifier = Gravity.Center
                    )
                    Column(modifier = ExpandedWidth wraps Spacing(left = 8.dp)) {
                        Text(text = item.city)
                        Text(text = item.tempFahrenheit.toString(), modifier = Gravity.End)
                    }
                }
            }

        }
    }
}

@Preview("Forecast List Item")
@Composable
fun ForecastListItemPreview() {
    ForecastListItem(item = sampleWeather)
}


@Composable
fun ForecastList(
    modifier: Modifier = ExpandedHeight,
    forecast: List<Weather>,
    onClick: ((Weather) -> Unit)? = null
) {
    Column {
        TopAppBar(title = { Text(text = +stringResource(R.string.app_name)) })
        VerticalScroller(modifier = modifier) {
            Column {
                forecast.forEach {
                    ForecastListItem(
                        item = it,
                        onClick = onClick
                    )
                }
            }
        }
    }

}

@Preview("Forecast List")
@Composable
fun ForecastListPreview() {
    ForecastList(forecast = generateSequence {
        sampleWeather
    }.take(30).toList()) {
        Navigation.navigateTo(
            Screen.Forecast(
                weather = it
            )
        )
    }
}
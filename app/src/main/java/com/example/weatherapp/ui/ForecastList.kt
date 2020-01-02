package com.example.weatherapp.ui

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.core.Modifier
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.AlertDialog
import androidx.ui.material.Button
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.TopAppBar
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Surface
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.VectorImage
import com.example.weatherapp.ViewModel
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.sampleWeather

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
    viewModel: ViewModel,
    modifier: Modifier = ExpandedHeight,
    onClick: ((Weather) -> Unit)? = null
) {
    Column {
        TopAppBar(title = { Text(text = +stringResource(R.string.app_name)) })
        Crossfade(current = viewModel.status) { isLoading ->
            when (isLoading) {
                ViewModel.Status.Loading -> Loading()
                ViewModel.Status.Loaded -> List(modifier, viewModel, onClick)
                else -> {
                    // Snackbar will be available on dev04
                    AlertDialog(onCloseRequest = {},
                        text = { Text(text = +stringResource(R.string.error)) },
                        confirmButton = {
                            Button(
                                text = +stringResource(R.string.try_again),
                                onClick = { viewModel.fetch() })
                        },
                        dismissButton = { Button(text = +stringResource(android.R.string.cancel)) }
                    )
                }
            }
        }

    }
}

@Composable
private fun List(
    modifier: Modifier,
    viewModel: ViewModel,
    onClick: ((Weather) -> Unit)?
) {
    VerticalScroller(modifier = modifier) {
        Column {
            viewModel.forecast.forEach {
                ForecastListItem(
                    item = it,
                    onClick = onClick
                )
            }
        }
    }
}

@Preview
@Composable
fun Loading() {
    Container(modifier = Expanded) {
        Center {
            //Indeterminate progress is not working nice
            CircularProgressIndicator(progress = 0.75f)
        }
    }

}

package com.example.weatherapp.ui

import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.core.Modifier
import androidx.ui.core.Text
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.VectorImage
import com.example.weatherapp.ViewModel
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.sampleWeather

@Composable
fun ForecastListItem(item: Weather, onClick: ((Weather) -> Unit)? = null) {
    Ripple(bounded = true) {
        Clickable(onClick = { onClick?.invoke(item) }) {
            Surface(
                shape = RoundedCornerShape(5.dp),
                elevation = 5.dp,
                modifier = LayoutPadding(8.dp)
            ) {
                Row(modifier = LayoutPadding(16.dp)) {
                    VectorImage(
                        id = item.condition.icon,
                        modifier = LayoutGravity.Center
                    )
                    Column(modifier = LayoutWidth.Fill + LayoutPadding(start = 8.dp)) {
                        Text(text = item.city)
                        Text(text = item.tempFahrenheit.toString(), modifier = LayoutGravity.End)
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
    modifier: Modifier = LayoutHeight.Fill,
    onClick: ((Weather) -> Unit)? = null
) {
    Column {
        TopAppBar(title = { Text(text = stringResource(R.string.app_name)) })
        Crossfade(current = viewModel.status) { isLoading ->
            when (isLoading) {
                ViewModel.Status.Loading -> Loading()
                ViewModel.Status.Loaded -> List(modifier, viewModel, onClick)
                else -> {
                    Snackbar(
                        text = { Text(text = stringResource(R.string.error)) },
                        action = {
                            TextButton(
                                contentColor = snackbarPrimaryColorFor(MaterialTheme.colors()),
                                onClick = viewModel::fetch
                            ) {
                                Text(text = stringResource(R.string.try_again))
                            }
                        },
                        modifier = LayoutAlign.Bottom
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
    Container(modifier = LayoutSize.Fill) {
        Center {
            //Indeterminate progress is not working nice
            CircularProgressIndicator(progress = 0.75f)
        }
    }

}

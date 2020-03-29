package com.example.weatherapp

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Icon
import androidx.ui.graphics.Color
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.vectorResource

@Composable
fun VectorImageButton(@DrawableRes id: Int, onClick: () -> Unit) {
    Ripple(bounded = false) {
        Clickable(onClick = onClick) {
            VectorImage(id = id)
        }
    }
}

@Composable
fun VectorImage(
    modifier: Modifier = Modifier.None,
    @DrawableRes id: Int,
    tint: Color = Color.Transparent
) {
    val vector = vectorResource(id)
    Icon(icon = vector, tint = tint)
}

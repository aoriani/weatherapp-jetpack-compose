package com.example.weatherapp

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.drawVector
import androidx.ui.layout.Container
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.vectorResource

@Composable
fun VectorImage(
    modifier: Modifier = Modifier.None,
    @DrawableRes id: Int,
    tint: Color = Color.Transparent
) {
    val vector = vectorResource(id)
    with(DensityAmbient.current) {
        Container(width = vector.defaultWidth, height = vector.defaultHeight) {
            drawVector(vectorImage = vector)
        }
    }
}


@Composable
fun VectorImageButton(@DrawableRes id: Int, onClick: () -> Unit) {
    Ripple(bounded = false) {
        Clickable(onClick = onClick) {
            VectorImage(id = id)
        }
    }
}

package com.example.personalcoach.presenter.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.Dp

fun createRoundedImageVector(
    size: Dp,
    viewPort: Float,
    fill: Color
): ImageVector {
    return ImageVector.Builder(
        defaultHeight = size,
        defaultWidth = size,
        viewportHeight = viewPort,
        viewportWidth = viewPort
    ).run {
        addPath(
            pathData = addPathNodes("M25,25m-25,0a25,25 0,1 1,50 0a25,25 0,1 1,-50 0"),
            name = "",
            fill = SolidColor(fill),
        )
        addPath(
            pathData = addPathNodes("M22,32L28,26L22,20"),
            strokeLineJoin = StrokeJoin.Round,
            strokeLineWidth = 2f,
            stroke = SolidColor(Color.White),
            strokeLineCap = StrokeCap.Round,

        )
        build()
    }
}
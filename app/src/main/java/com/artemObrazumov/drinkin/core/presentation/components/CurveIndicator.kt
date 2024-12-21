package com.artemObrazumov.drinkin.core.presentation.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.core.presentation.models.ArcAngles

fun DrawScope.curveIndicator(
    color: Color,
    angles: ArcAngles,
    stroke: Dp = 8.dp,
    extraWidth: Float = 196f,
    height: Float = 784f,
    offsetY: Float = 0f
) {
    drawArc(
        useCenter = false,
        color = color,
        startAngle = angles.startAngle,
        sweepAngle = angles.sweepAngle,
        topLeft = Offset(
            x = -extraWidth / 2f,
            y = offsetY
        ),
        size = Size(
            width = size.width + extraWidth,
            height = height
        ),
        style = Stroke(width = stroke.toPx())
    )
}

fun calculateIndicatorAngles(
    index: Int,
    total: Int,
): ArcAngles {
    val single = 120f / total
    return ArcAngles(
        210f + single * index + single * 0.1f,
        single * 0.8f
    )
}
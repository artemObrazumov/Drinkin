package com.artemObrazumov.drinkin.core.presentation.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.curveCanvasBackground(
    color: Color,
    extraWidth: Float = 196f,
    height: Float = 784f,
    offsetY: Float = 0f
) {
    drawArc(
        useCenter = false,
        color = color,
        startAngle = 180f,
        sweepAngle = 180f,
        topLeft = Offset(
            x = -extraWidth / 2f,
            y = offsetY
        ),
        size = Size(
            width = size.width + extraWidth,
            height = height
        )
    )
    drawRect(
        color = color,
        topLeft = Offset(
            x = 0f,
            y = offsetY + height / 2 - 1
        ),
        size = Size(
            width = size.width,
            height = size.height
        )
    )
}
package com.artemObrazumov.drinkin.dashboard.presentation.products_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme
import kotlin.math.min

@Composable
fun ProductIndicator(
    totalItems: Int,
    currentItem: Int,
    modifier: Modifier = Modifier,
    indicatorRadius: Float = 8f,
    indicatorOutlineRadius: Float = indicatorRadius * 5,
    indicatorOutlineStroke: Float = 8f,
    indicatorColor: Color = Color.White,
    indicatorOutlineColor: Color = MaterialTheme.colorScheme.tertiary,
) {
    val items = min(totalItems, 5)
    Canvas(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val start = 0f + indicatorOutlineRadius
        val end = size.width - (indicatorRadius + indicatorOutlineRadius)
        val widthPerIndicator = (end - start) / (totalItems - 1)
        repeat(totalItems) { index ->
            drawCircle(
                color = if (index == currentItem) {
                    indicatorColor
                } else {
                    indicatorColor.copy(alpha = 0.5f)
                },
                radius = indicatorRadius,
                center = Offset(
                    x = start + widthPerIndicator * index,
                    y = size.height / 2
                )
            )
            if (index == currentItem) {
                drawCircle(
                    color = indicatorOutlineColor,
                    radius = indicatorOutlineRadius,
                    style = Stroke(
                        width = indicatorOutlineStroke
                    ),
                    center = Offset(
                        x = start + widthPerIndicator * index,
                        y = size.height / 2
                    )
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun ProductsIndicatorPreview() {
    DrinkinTheme {
        ProductIndicator(
            totalItems = 3,
            currentItem = 2,
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
        )
    }
}
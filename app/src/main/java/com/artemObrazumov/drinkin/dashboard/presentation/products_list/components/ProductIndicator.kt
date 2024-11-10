package com.artemObrazumov.drinkin.dashboard.presentation.products_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme
import kotlin.math.max
import kotlin.math.min

// indicator with 5 items visible at once
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
    val startIndex = if (totalItems - 1 - currentItem >= 2) {
        max(currentItem - 2, 0)
    } else {
        max(totalItems - 5, 0)
    }
    val endIndex = min(startIndex + 4, totalItems - 1)
    val indexesBeforeFirst = startIndex > 0
    val indexesAfterLast = endIndex < totalItems - 1
    val indices = IntRange(
        start = startIndex, endInclusive = endIndex
    )
    println(indices)
    Canvas(
        modifier = modifier.fillMaxWidth()
    ) {
        val start = 0f + indicatorOutlineRadius
        val end = size.width - (indicatorRadius + indicatorOutlineRadius)
        val widthPerIndicator = (end - start) / 4
        indices.forEach { index ->
            val isSmallerIndicator = (index != currentItem && ((index == indices.first && indexesBeforeFirst) || (index == indices.last && indexesAfterLast)))
            val middle = indices.first + (indices.last - indices.first) / 2
            val realIndex = index - middle + 2f
            drawCircle(
                color = if (index == currentItem) {
                    indicatorColor
                } else if (isSmallerIndicator) {
                    indicatorColor.copy(alpha = 0.3f)
                } else {
                    indicatorColor.copy(alpha = 0.6f)
                }, radius = if (isSmallerIndicator) {
                    indicatorRadius * 0.8f
                } else {
                    indicatorRadius
                }, center = Offset(
                    x = start + widthPerIndicator * realIndex, y = size.height / 2
                )
            )
            if (index == currentItem) {
                drawCircle(
                    color = indicatorOutlineColor, radius = indicatorOutlineRadius, style = Stroke(
                        width = indicatorOutlineStroke
                    ), center = Offset(
                        x = start + widthPerIndicator * realIndex, y = size.height / 2
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
            totalItems = 3, currentItem = 2, modifier = Modifier
                .width(100.dp)
                .height(50.dp)
        )
    }
}
package com.artemObrazumov.drinkin.product.presentation.products_list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.core.presentation.components.calculateIndicatorAngles
import com.artemObrazumov.drinkin.core.presentation.components.curveCanvasBackground
import com.artemObrazumov.drinkin.core.presentation.components.curveIndicator
import com.artemObrazumov.drinkin.core.presentation.models.ArcAngles
import com.artemObrazumov.drinkin.product.presentation.models.CategoryUi
import kotlin.math.abs
import kotlin.math.floor

@Composable
fun CategoriesList(
    categories: List<CategoryUi>,
    backgroundColor: Color,
    indicatorColor: Color,
    modifier: Modifier = Modifier,
    itemOffsetDp: Int,
    selectedCategoryIndex: Int,
    onCategoryClicked: (category: CategoryUi) -> Unit
) {
    val angles = calculateIndicatorAngles(
        index = selectedCategoryIndex,
        total = categories.size
    )
    var prevIndex by remember {
        mutableIntStateOf(0)
    }
    var indicatorMovingForward by remember {
        mutableStateOf(true)
    }
    if (prevIndex != selectedCategoryIndex) {
        indicatorMovingForward = prevIndex < selectedCategoryIndex
        prevIndex = selectedCategoryIndex
    }
    val startAngle = angles.startAngle
    val startAngleAnimated by animateFloatAsState(
        targetValue = startAngle,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = if (indicatorMovingForward) {
                0
            } else {
                60
            }
        ),
        label = ""
    )
    val endAngle = angles.startAngle + angles.sweepAngle
    val endAngleAnimated by animateFloatAsState(
        targetValue = endAngle,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = if (indicatorMovingForward) {
                60
            } else {
                0
            }
        ),
        label = ""
    )
    Box (
        modifier = modifier
            .drawBehind {
                curveCanvasBackground(
                    color = backgroundColor,
                    offsetY = 24.dp.toPx()
                )
                curveIndicator(
                    color = indicatorColor,
                    stroke = 6.dp,
                    offsetY = 27.dp.toPx(),
                    angles = ArcAngles(
                        startAngleAnimated,
                        endAngleAnimated - startAngleAnimated
                    )
                )
            }
    ) {
        val fraction = when (categories.size) {
            2 -> 0.6f
            3 -> 0.8f
            else -> 0.95f
        }
        val middleIndex = (categories.size-1) / 2f
        Row (
            modifier = Modifier
                .fillMaxWidth(fraction)
                .fillMaxHeight()
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            categories.forEachIndexed { index, category ->
                CategoryItem(
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer {
                            val translationYDp = if (categories.size % 2 == 0) {
                                floor(abs(middleIndex - index)) * itemOffsetDp + itemOffsetDp
                            } else {
                                floor(abs(middleIndex - index)) * itemOffsetDp + itemOffsetDp
                            }
                            translationY = translationYDp.dp.toPx()
                        },
                    category = category,
                    onClick = {
                        onCategoryClicked(category)
                    }
                )
                if (index != categories.size - 1) {
                    Spacer(
                        modifier = Modifier.width(when(categories.size) {
                            5 -> 12.dp
                            else -> 28.dp
                        })
                    )
                }
            }
        }
    }
}
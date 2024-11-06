package com.artemObrazumov.drinkin.dashboard.presentation.products_list.components

import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutElastic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.round

@Composable
fun TransitionCircle(
    center: Offset,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    onTransitionEnd: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary
) {
    var maxRadius by remember {
        mutableFloatStateOf(0f)
    }
    val radius by animateFloatAsState(
        targetValue = if (isActive) {
            maxRadius
        } else {
            0f
        },
        label = "Transition circle",
        animationSpec = tween(
            durationMillis = 600
        ),
        finishedListener = { onTransitionEnd() }
    )
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                maxRadius = layoutCoordinates.size.height * 1.2f
            }
    ) {
        drawCircle(
            color = color,
            center = center,
            radius = radius
        )
    }
}

@Composable
fun TransitionImage(
    image: ImageBitmap,
    imageSize: IntSize,
    imagePosition: IntOffset,
    moveToTop: Boolean,
    modifier: Modifier = Modifier,
    outlineWidthPx: Float = 8f,
) {
    var imageGlobalPosition by remember {
        mutableStateOf(IntOffset.Zero)
    }

    var yOffsetValue by remember {
        mutableIntStateOf(0)
    }

    val yOffset by animateIntAsState(
        targetValue = yOffsetValue,
        label = "Transition image",
        animationSpec = tween(
            durationMillis = 550,
            delayMillis = 50
        )
    )

    LaunchedEffect(moveToTop) {
        yOffsetValue = if (moveToTop) {
            imageSize.height * (-2)
        } else {
            0
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                imageGlobalPosition = imagePosition -
                        layoutCoordinates
                            .positionInRoot()
                            .round()
            }
    ) {
        val circlePath = Path().apply {
            val radius = imageSize.width / 2
            val ratio = 2f / 3f
            val left = imageGlobalPosition.x.toFloat()
            val right = imageGlobalPosition.x.toFloat() + imageSize.width.toFloat()
            val bottom = imageGlobalPosition.y.toFloat() + imageSize.width / ratio - outlineWidthPx
            val top = bottom - radius * 2
            addRect(
                rect = Rect(
                    left = left,
                    right = right,
                    top = 0f,
                    bottom = bottom - radius
                )
            )
            addOval(
                Rect(
                    left = left,
                    right = right,
                    top = top,
                    bottom = bottom
                )
            )
        }
        clipPath(circlePath) {
            drawImage(
                image = image,
                dstOffset = imageGlobalPosition.copy(
                    y = imageGlobalPosition.y + yOffset
                ),
                dstSize = imageSize
            )
        }
    }
}
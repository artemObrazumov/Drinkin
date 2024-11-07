package com.artemObrazumov.drinkin.dashboard.presentation.products_list.components

import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
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
    var maxRadius by rememberSaveable {
        mutableFloatStateOf(0f)
    }
    var radius by rememberSaveable {
        mutableFloatStateOf(0f)
    }
    LaunchedEffect(isActive) {
        radius = if (isActive) {
            maxRadius
        } else {
            0f
        }
    }
    val radiusAnimated by animateFloatAsState(
        targetValue = radius,
        label = "Transition circle",
        animationSpec = tween(
            durationMillis = 700
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
            radius = radiusAnimated
        )
    }
}

@Composable
fun TransitionImage(
    @DrawableRes
    imageRes: Int,
    imageSize: IntSize,
    imagePosition: IntOffset,
    moveToTop: Boolean,
    modifier: Modifier = Modifier,
    outlineWidthPx: Float = 8f,
) {
    val context = LocalContext.current
    var imageGlobalPosition by remember {
        mutableStateOf(IntOffset.Zero)
    }
    var yOffsetValue by rememberSaveable {
        mutableIntStateOf(0)
    }
    val yOffset by animateIntAsState(
        targetValue = yOffsetValue,
        label = "Transition image",
        animationSpec = if (moveToTop) {
            tween(
                durationMillis = 550,
                delayMillis = 100
            )
        } else {
            tween(
                durationMillis = 400
            )
        }
    )

    LaunchedEffect(moveToTop) {
        yOffsetValue = if (moveToTop) {
            imageSize.height * (-2)
        } else {
            0
        }
    }

    val image by remember {
        mutableStateOf(
            BitmapFactory
                .decodeResource(context.resources, imageRes)
                .asImageBitmap()
        )
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
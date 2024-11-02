package com.artemObrazumov.drinkin.dashboard.presentation.products_list.components

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import androidx.annotation.FloatRange
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.zIndex
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.dashboard.domain.models.Product
import com.artemObrazumov.drinkin.dashboard.presentation.models.ProductUi
import com.artemObrazumov.drinkin.dashboard.presentation.models.toProductUi
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ProductItem(
    productUi: ProductUi,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    @FloatRange(from = 0.0, to = 1.0) imageYOffset: Float = 0.1f,
    background: Color = MaterialTheme.colorScheme.secondary,
    shadowColor: Color = MaterialTheme.colorScheme.onSecondary,
    outlineColor: Color = MaterialTheme.colorScheme.outline,
    outlineWidthPx: Float = 8f
) {
    val context = LocalContext.current
    val image by remember {
        mutableStateOf(
            BitmapFactory
                .decodeResource(context.resources, productUi.imageRes)
                .asImageBitmap()
        )
    }
    val shadowRadius = 64f
    val paint: Paint = remember { Paint() }
    val maskFilter = remember {
        BlurMaskFilter(shadowRadius, BlurMaskFilter.Blur.NORMAL)
    }
    val commonModifier = modifier
        .fillMaxWidth()
        .aspectRatio(2f / 3f)
    Box {
        Canvas(
            modifier = commonModifier
                .drawBehind {
                    drawIntoCanvas { canvas ->
                        val frameworkPaint = paint.asFrameworkPaint()
                        frameworkPaint.maskFilter = maskFilter
                        frameworkPaint.color = shadowColor.toArgb()
                        val radius = size.width / 2 + shadowRadius
                        canvas.drawCircle(
                            paint = paint,
                            center = Offset(
                                x = size.width / 2,
                                y = size.height - size.width / 2
                            ),
                            radius = radius
                        )
                    }
                }
        ) {
            val radius = size.width / 2
            drawCircle(
                color = background,
                center = Offset(
                    x = size.width / 2,
                    y = size.height - radius
                ),
                radius = radius
            )
            drawArc(
                useCenter = false,
                color = outlineColor,
                startAngle = 180f,
                sweepAngle = 180f,
                style = Stroke(width = outlineWidthPx),
                size = Size(
                    width = radius * 2 - outlineWidthPx * 1f,
                    height = radius * 2
                ),
                topLeft = Offset(
                    x = outlineWidthPx * 0.5f,
                    y = radius + outlineWidthPx / 1.5f - 2f
                )
            )
        }
        Canvas(
            modifier = commonModifier
                .sharedElement(
                    rememberSharedContentState(key = "background${productUi.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        ) {
            val radius = size.width / 4
            drawCircle(
                color = background,
                center = Offset(
                    x = size.width / 2,
                    y = size.height - radius
                ),
                radius = radius
            )
        }
        Canvas(
            modifier = commonModifier
                .clip(
                    RoundedCornerShape(
                        bottomStartPercent = 100,
                        bottomEndPercent = 100
                    )
                )
        ) {
            val radius = size.width / 2
            val imageCoefficient = size.width / image.width
            drawImage(
                image = image,
                dstOffset = IntOffset(
                    x = 0,
                    y = (size.height * imageYOffset).toInt()
                ),
                dstSize = IntSize(
                    width = size.width.toInt(),
                    height = (image.height * imageCoefficient).toInt()
                )
            )
            drawArc(
                useCenter = false,
                color = outlineColor,
                startAngle = 0f,
                sweepAngle = 180f,
                style = Stroke(width = outlineWidthPx),
                size = Size(
                    width = radius * 2 - outlineWidthPx * 1f,
                    height = radius * 2
                ),
                topLeft = Offset(
                    x = outlineWidthPx * 0.5f,
                    y = radius - outlineWidthPx / 2 + 2f
                )
            )
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@PreviewLightDark
@Composable
fun DrinkItemPreview() {
    DrinkinTheme {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = true,
                label = "DrinksListScreenPreview"
            ) {
                ProductItem(
                    productUi = PRODUCT,
                    animatedVisibilityScope = this@AnimatedContent
                )
            }
        }
    }
}

internal val PRODUCT = Product(
    id = 1,
    name = "Test drink",
    price = 30f,
    salePrice = null,
    category = "AAA",
    imageRes = R.drawable.cup
).toProductUi("$")
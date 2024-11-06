package com.artemObrazumov.drinkin.dashboard.presentation.product_details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.dashboard.presentation.models.ProductUi
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.PRODUCTS
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme

@Composable
fun ProductDetailsScreen(
    productUi: ProductUi,
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current

    var appearing by remember {
        mutableStateOf(true)
    }
    var animationDuration by remember {
        mutableIntStateOf(1000)
    }
    var circleRadius by remember {
        mutableStateOf(0.dp)
    }
    val circleRadiusAnimated by animateDpAsState(
        targetValue = circleRadius,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = EaseOutCubic
        ),
        label = "",
        finishedListener = {
            if (!appearing) {
                onGoBack()
            }
        }
    )

    var imageXOffset by remember {
        mutableStateOf(configuration.screenWidthDp.dp)
    }
    val imageXOffsetAnimated by animateDpAsState(
        targetValue = imageXOffset,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = EaseOutCubic
        ),
        label = ""
    )

    LaunchedEffect(appearing) {
        circleRadius = if (appearing) {
            configuration.screenWidthDp.dp
        } else {
            0.dp
        }

        imageXOffset = if (appearing) {
            0.dp
        } else {
            configuration.screenWidthDp.dp
        }

        animationDuration = if (appearing) {
            1000
        } else {
            400
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .drawBehind {
                drawCircle(
                    color = Color.White,
                    radius = circleRadiusAnimated.toPx(),
                    center = Offset(
                        x = (configuration.screenWidthDp + 8).dp.toPx(),
                        y = 0f
                    )
                )
            }
    ) {
        item {
            Row {
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = productUi.imageRes),
                    contentDescription = productUi.name,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .offset {
                            IntOffset(
                                x = imageXOffsetAnimated.roundToPx(),
                                y = 0
                            )
                        }
                        .padding(top = 48.dp)
                )
            }
        }
    }

    BackHandler {
        appearing = false
    }
}

@PreviewLightDark
@Composable
fun ProductDetailsScreenPreview() {
    DrinkinTheme {
        Surface {
            ProductDetailsScreen(
                productUi = PRODUCTS.first()
            )
        }
    }
}
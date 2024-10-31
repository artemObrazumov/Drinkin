package com.artemObrazumov.drinkin.dashboard.presentation.products_list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.artemObrazumov.drinkin.core.presentation.components.curveCanvasBackground
import com.artemObrazumov.drinkin.dashboard.presentation.models.ProductUi
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sin

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductsPager(
    pagerState: PagerState,
    backgroundColor: Color,
    itemsPaddingDp: Int,
    drinkItem: (page: Int) -> ProductUi,
    modifier: Modifier = Modifier
) {
    val localDensity = LocalDensity.current
    var pagerWidth by remember {
        mutableStateOf(0.dp)
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .drawBehind {
                curveCanvasBackground(
                    color = backgroundColor,
                    extraWidth = 56f,
                    offsetY = 24.dp.toPx()
                )
            }
            .onGloballyPositioned { coordinates ->
                pagerWidth = with(localDensity) { coordinates.size.width.toDp() }
            },
        contentPadding = PaddingValues(horizontal = pagerWidth / 5),
        pageSpacing = 16.dp,
        verticalAlignment = Alignment.Top
    ) { page ->
        val realPageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                .absoluteValue
        val pageOffset = realPageOffset.coerceIn(0f, 1f)
        val yOffset = animateFloatAsState(
            targetValue = if (pageOffset < 0.5f) 0f else 0.3f,
            label = "$page yOffset Animation",
            animationSpec = tween(durationMillis = 200)
        )
        val angle = lerp(
            start = 0f,
            stop = PI.toFloat() / 2,
            fraction = 1f - pageOffset
        )
        val sinY = sin(angle)
        ProductItem(
            productUi = drinkItem(page),
            modifier = Modifier
                .padding(
                    top = (itemsPaddingDp - sinY * itemsPaddingDp).dp
                )
            ,
            imageYOffset = yOffset.value
        )
    }
}
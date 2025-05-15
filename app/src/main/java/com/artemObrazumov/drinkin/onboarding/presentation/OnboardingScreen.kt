package com.artemObrazumov.drinkin.onboarding.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.presentation.components.PagerIndicator
import com.artemObrazumov.drinkin.onboarding.presentation.components.OnboardingPageContent
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun OnboardingScreen(
    state: OnboardingScreenState,
    modifier: Modifier = Modifier,
    onOnboardingFinished: () -> Unit = {},
    onFinished: () -> Unit = {}
) {
    LaunchedEffect(state.finishedOnboarding) {
        if (state.finishedOnboarding) {
            onOnboardingFinished.invoke()
        }
    }
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        val content = remember { OnboardingContent.pages }
        val pagerState = rememberPagerState { content.size }
        val scope = rememberCoroutineScope()
        val fraction = 1f - abs(pagerState.currentPageOffsetFraction * 2)

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.65f)
                .drawBehind {
                    drawCircle(
                        color = Color.White,
                        radius = constraints.minWidth * 1f,
                        center = Offset(
                            x = constraints.minWidth * 0.6f,
                            y = fraction * 96f
                        )
                    )
                }
                .padding(top = 84.dp + (fraction * 26).dp)
                .padding(end = 16.dp + (fraction * 8).dp)
                .align(Alignment.TopEnd)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(fraction),
                painter = painterResource(content[pagerState.currentPage].imageRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )
        }

        Column(
            modifier = Modifier
                .padding(bottom = 48.dp)
        ) {
            HorizontalPager(
                modifier = Modifier
                    .weight(1f),
                state = pagerState
            ) { page ->
                OnboardingPageContent(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    page = content[page]
                )
            }
            Spacer(
                modifier = Modifier.height(64.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onFinished.invoke()
                        },
                    text = stringResource(R.string.skip),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                PagerIndicator(
                    modifier = Modifier.weight(2f),
                    totalItems = pagerState.pageCount,
                    currentItem = pagerState.currentPage
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (pagerState.canScrollForward) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                onFinished.invoke()
                            }
                        },
                    text = if (pagerState.currentPage < pagerState.pageCount - 1) {
                        stringResource(R.string.next)
                    } else {
                        stringResource(R.string.start)
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
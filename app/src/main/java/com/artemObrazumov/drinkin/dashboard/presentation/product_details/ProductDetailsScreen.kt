package com.artemObrazumov.drinkin.dashboard.presentation.product_details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ProductDetailsScreen(
    a: Int,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
) {
    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = "background$a"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = {initial, target ->
                        // Move vertically first then horizontally
                        keyframes {
                            durationMillis = 1000
                            initial at 0
                            Rect(initial.left, target.top, initial.left + target.width, target.bottom) at 1000
                        }
                    }
                )
                .fillMaxSize()
        ) {
            drawCircle(color = backgroundColor, radius = size.height)
        }
        Image(
            painter = painterResource(id = R.drawable.cup),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@PreviewLightDark
@Composable
fun ProductDetailsScreenPreview() {
    DrinkinTheme {
        //ProductDetailsScreen()
    }
}
package com.artemObrazumov.drinkin.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.ui.theme.beansBackgroundColor

@Composable
fun BeansBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Image(
            modifier = Modifier
                .padding(top = 148.dp)
                .fillMaxWidth(0.7f)
                .aspectRatio(1f)
                .graphicsLayer {
                    translationX = -96.dp.toPx()
                }
                .rotate(135f),
            painter = painterResource(id = R.drawable.beans),
            contentDescription = null,
            colorFilter = ColorFilter.tint(beansBackgroundColor)
        )
    }
}
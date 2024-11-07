package com.artemObrazumov.drinkin.dashboard.presentation.product_details.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun ProductImage(
    @DrawableRes imageRes: Int,
    circleRadius: Dp,
    circleXOffset: Dp,
    imageXOffset: Dp,
    modifier: Modifier = Modifier,
    name: String? = null,
) {
    Row(
        modifier = modifier
            .drawBehind {
                drawCircle(
                    color = Color.White,
                    radius = circleRadius.toPx(),
                    center = Offset(
                        x = circleXOffset.toPx(),
                        y = 0f
                    )
                )
            }
    ) {
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .offset {
                    IntOffset(
                        x = imageXOffset.roundToPx(),
                        y = 0
                    )
                }
                .padding(top = 48.dp)
        )
    }
}
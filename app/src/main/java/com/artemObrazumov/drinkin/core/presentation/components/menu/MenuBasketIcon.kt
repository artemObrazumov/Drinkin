package com.artemObrazumov.drinkin.core.presentation.components.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.R

@Composable
fun MenuBasketIcon(
    modifier: Modifier = Modifier,
    hasElements: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(id = R.drawable.shopping_basket),
            tint = MaterialTheme.colorScheme.tertiaryContainer,
            contentDescription = null
        )
        if (hasElements) {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        translationX = -6.dp.toPx()
                        translationY = 4.dp.toPx()
                    }
                    .size(16.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
        }
    }
}
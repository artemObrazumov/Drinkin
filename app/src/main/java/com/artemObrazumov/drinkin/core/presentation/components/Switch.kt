package com.artemObrazumov.drinkin.core.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Switch(
    options: Pair<String, String>,
    currentOption: Int,
    onOptionClicked: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    indicatorColor: Color = MaterialTheme.colorScheme.primary
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .shadow(
                elevation = 8.dp,
                spotColor = MaterialTheme.colorScheme.primary,
                ambientColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .background(Color.White)
            .padding(16.dp)
    ) {
        val halfWidth = this.maxWidth / 2
        val indicatorOffset by animateDpAsState(
            targetValue = if (currentOption == 1) halfWidth else 0.dp
        )
        var indicatorHeight by remember { mutableStateOf(0.dp) }
        Box(
            modifier = Modifier
                .width(halfWidth)
                .height(indicatorHeight)
                .offset {
                    IntOffset(
                        x = indicatorOffset.roundToPx(),
                        y = 0
                    )
                }
                .background(
                    color = indicatorColor,
                    shape = CircleShape
                )
        )
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .onGloballyPositioned {
                    indicatorHeight = it.size.height.dp
                }
        ) {
            val firstTextColor by animateColorAsState(
                if (currentOption == 0) Color.White else Color.Black
            )
            val secondTextColor by animateColorAsState(
                if (currentOption == 1) Color.White else Color.Black
            )
            Text(
                text = options.first,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onOptionClicked(0)
                    },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center,
                color = firstTextColor
            )
            Text(
                text = options.second,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onOptionClicked(1)
                    },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center,
                color = secondTextColor
            )
        }

    }
}
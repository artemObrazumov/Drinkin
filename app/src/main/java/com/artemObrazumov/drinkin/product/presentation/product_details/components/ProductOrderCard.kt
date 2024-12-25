package com.artemObrazumov.drinkin.product.presentation.product_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.app.ui.theme.DrinkinTheme
import com.artemObrazumov.drinkin.app.ui.theme.darkTextColor
import com.artemObrazumov.drinkin.product.presentation.product_details.ButtonState

@Composable
fun ProductOrderCard(
    height: Dp,
    heightPadding: Dp,
    count: Int,
    buttonEnabled: Boolean,
    buttonState: ButtonState,
    onSubtract: () -> Unit,
    onAdd: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(height + heightPadding)
            .background(
                Brush.linearGradient(
                    0f to Color.Transparent,
                    0.4f to MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                    start = Offset.Zero,
                    end = Offset(
                        x = 0f,
                        y = Float.POSITIVE_INFINITY
                    )
                )
            )
            .padding(top = 4.dp + heightPadding)
            .padding(bottom = 28.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                onClick = { onSubtract() },
                modifier = Modifier
                    .shadow(
                        elevation = 20.dp,
                        spotColor = MaterialTheme.colorScheme.primary,
                        ambientColor = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painterResource(id = R.drawable.minus),
                    contentDescription = "subtract"
                )
            }
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )
            Text(
                modifier = Modifier
                    .defaultMinSize(minWidth = 40.dp),
                text = count.toString(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )
            FilledIconButton(
                onClick = { onAdd() },
                modifier = Modifier
                    .shadow(
                        elevation = 20.dp,
                        spotColor = MaterialTheme.colorScheme.primary,
                        ambientColor = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "add"
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
            val isEnabled = buttonEnabled && buttonState != ButtonState.Loading ||
                    buttonState == ButtonState.Success || buttonState == ButtonState.Failure
            Button(
                onClick = { onAddToCart() },
                modifier = Modifier
                    .fillMaxHeight()
                    .shadow(
                        elevation = 20.dp,
                        spotColor = MaterialTheme.colorScheme.primary,
                        ambientColor = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                enabled = isEnabled
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    ButtonContent(
                        buttonState = buttonState,
                        enabled = isEnabled
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonContent(
    buttonState: ButtonState,
    enabled: Boolean
) {
    val textColor = if (enabled) {
        Color.White
    } else {
        darkTextColor
    }
    when (buttonState) {
        ButtonState.Idle -> {
            Text(
                text = "Add to Order",
                color = textColor
            )
        }

        ButtonState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
            )
        }

        ButtonState.Success -> {
            Text(
                text = "Added to Order!",
                color = textColor
            )
        }

        ButtonState.Failure -> {
            Text(
                text = "Couldn't add",
                color = textColor
            )
        }
    }
}

@PreviewLightDark
@Composable
fun ProductOrderCardPreview() {
    DrinkinTheme {
        Surface {
            ProductOrderCard(
                height = 100.dp,
                heightPadding = 16.dp,
                count = 1,
                buttonState = ButtonState.Success,
                buttonEnabled = true,
                onSubtract = {},
                onAdd = {},
                onAddToCart = {}
            )
        }
    }
}

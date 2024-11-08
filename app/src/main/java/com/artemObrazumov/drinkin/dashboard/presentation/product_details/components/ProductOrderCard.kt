package com.artemObrazumov.drinkin.dashboard.presentation.product_details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme

@Composable
fun ProductOrderCard(
    height: Dp,
    count: Int,
    onSubtract: () -> Unit,
    onAdd: () -> Unit,
    onAddToOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .padding(top = 4.dp)
            .padding(bottom = 32.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
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
                onClick = { },
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
                    contentDescription = "subtract"
                )
            }
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )
            Text(
                text = count.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )
            FilledIconButton(
                onClick = { },
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
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxHeight()
                    .shadow(
                        elevation = 20.dp,
                        spotColor = MaterialTheme.colorScheme.primary,
                        ambientColor = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = "Add to Order"
                )
            }
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
                count = 1,
                onSubtract = {},
                onAdd = {},
                onAddToOrder = {}
            )
        }
    }
}
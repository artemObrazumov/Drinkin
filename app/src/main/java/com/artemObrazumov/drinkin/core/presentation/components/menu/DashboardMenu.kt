package com.artemObrazumov.drinkin.core.presentation.components.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.app.ui.theme.darkTextColor

@Composable
fun DashboardMenu(
    address: String,
    modifier: Modifier = Modifier,
    basketHasElements: Boolean = false,
    onAddressIconClicked: () -> Unit,
    onProfileIconClicked: () -> Unit,
    onCartIconClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(vertical = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onAddressIconClicked() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.Place,
                contentDescription = "address",
                tint = MaterialTheme.colorScheme.tertiaryContainer
            )
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            Text(
                text = address,
                minLines = 1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                color = darkTextColor
            )
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )
        MenuProfileIcon {
            onProfileIconClicked()
        }
        MenuBasketIcon(
            hasElements = basketHasElements
        ) {
            onCartIconClicked()
        }
    }
}
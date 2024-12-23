package com.artemObrazumov.drinkin.cart.presentation.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.ui.theme.darkTextColor

@Composable
fun EmptyCartMessage(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val color = darkTextColor.copy(alpha = 0.6f)
            Image(
                modifier = Modifier
                    .size(96.dp),
                colorFilter = ColorFilter.tint(color),
                painter = painterResource(id = R.drawable.cart_empty),
                contentDescription = "cart is empty"
            )
            Text(
                text = "Cart is empty",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 28.sp
                ),
                color = color
            )
        }
    }
}
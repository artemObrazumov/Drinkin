package com.artemObrazumov.drinkin.dashboard.presentation.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductInCart

@Composable
fun ProductInCartItem(
    product: ProductInCart,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        val secondaryColor = MaterialTheme.colorScheme.secondary
        Column(
            modifier = Modifier
                .weight(3f)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary)
                .drawBehind {
                    drawCircle(
                        color = secondaryColor,
                        radius = size.width,
                        center = Offset(
                            x = size.width,
                            y = size.height
                        )
                    )
                }
                .aspectRatio(3f / 4f, matchHeightConstraintsFirst = true)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name
            )
        }
        Spacer(
            modifier = Modifier.width(16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(6f)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                letterSpacing = 1.sp,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "View details",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    onClick = {  },
                    modifier = Modifier
                        .shadow(
                            elevation = 20.dp,
                            spotColor = MaterialTheme.colorScheme.primary,
                            ambientColor = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .size(28.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.minus),
                        modifier = Modifier
                            .size(12.dp),
                        contentDescription = "subtract"
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(12.dp)
                )
                Text(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 32.dp),
                    text = product.quantity.toString(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(
                    modifier = Modifier
                        .width(12.dp)
                )
                FilledIconButton(
                    onClick = {  },
                    modifier = Modifier
                        .shadow(
                            elevation = 20.dp,
                            spotColor = MaterialTheme.colorScheme.primary,
                            ambientColor = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .size(28.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        modifier = Modifier
                            .size(20.dp),
                        contentDescription = "add"
                    )
                }
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = product.price.asFormattedPrice(PRICE_UNIT).formatted,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}
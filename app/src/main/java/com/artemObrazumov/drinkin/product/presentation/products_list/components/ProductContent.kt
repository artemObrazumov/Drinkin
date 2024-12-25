package com.artemObrazumov.drinkin.product.presentation.products_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT
import com.artemObrazumov.drinkin.app.ui.theme.DrinkinTheme

@Composable
fun ProductContent(
    name: String,
    price: FormattedValue<Float>,
    salePrice: FormattedValue<Float>?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            letterSpacing = 3.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = price.formatted,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
            if (salePrice != null) {
                Spacer(
                    modifier = Modifier
                        .width(4.dp)
                )
                Text(
                    text = salePrice.formatted,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp,
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun DrinkContentPreview() {
    DrinkinTheme {
        ProductContent(
            name = "Drink 1",
            price = 30f.asFormattedPrice(PRICE_UNIT),
            salePrice = 35f.asFormattedPrice(PRICE_UNIT)
        )
    }
}
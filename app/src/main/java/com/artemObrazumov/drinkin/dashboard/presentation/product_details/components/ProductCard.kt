package com.artemObrazumov.drinkin.dashboard.presentation.product_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.dashboard.presentation.models.CustomizableParameterOptionUi
import com.artemObrazumov.drinkin.dashboard.presentation.models.CustomizableParameterUi
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.PRODUCT_DETAILS
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme

@Composable
fun ProductCard(
    name: String,
    description: String,
    price: FormattedValue<Float>,
    salePrice: FormattedValue<Float>?,
    customizableParameters: List<CustomizableParameterUi>,
    selectedParameters: Map<String, Int?>,
    onParameterSelect: (parameter: String, optionIndex: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
        ) {
            Row {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .weight(3f),
                    letterSpacing = 2.sp
                )
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = price.formatted,
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
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            letterSpacing = 1.sp,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                letterSpacing = 1.sp
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            customizableParameters.forEach { parameter ->
                Text(
                    text = parameter.name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    letterSpacing = 1.sp
                )
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )
                CustomizableParameter(
                    parameter = parameter,
                    selectedParameters = selectedParameters,
                    onParameterSelect = onParameterSelect
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomizableParameter(
    parameter: CustomizableParameterUi,
    selectedParameters: Map<String, Int?>,
    onParameterSelect: (parameter: String, optionIndex: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        parameter.options.forEachIndexed { index, option ->
            val isSelected = selectedParameters[parameter.name] == index
            CustomizableParameterOption(
                option = option,
                isActive = isSelected,
                modifier = Modifier
                    .fillMaxWidth(0.2f),
                onClick = {
                    onParameterSelect(parameter.name, index)
                }
            )
        }
    }
}

@Composable
fun CustomizableParameterOption(
    option: CustomizableParameterOptionUi,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = option.imageRes),
            contentDescription = option.name,
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    spotColor = MaterialTheme.colorScheme.primary,
                    ambientColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .clickable {
                    onClick()
                }
                .background(
                    color = if (isActive) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    },
                    shape = CircleShape
                )
                .padding(16.dp)
                .aspectRatio(1f),
            colorFilter = ColorFilter.tint(
                color = if (isActive) {
                    Color.White
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Text(
            text = option.name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            letterSpacing = 1.sp
        )
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
        Text(
            text = option.detail,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            modifier = Modifier
                .alpha(0.4f)
        )
    }
}

@PreviewLightDark
@Composable
fun ProductCardPreview() {
    DrinkinTheme {
        Surface {
            val product = PRODUCT_DETAILS
                .copy(
                    salePrice = 35f.asFormattedPrice("$")
                )
            ProductCard(
                name = product.name,
                description = product.description,
                price = product.price,
                salePrice = product.salePrice,
                customizableParameters = product.customizableParams,
                selectedParameters = mapOf(
                    "Size options" to 2
                ),
                onParameterSelect = { _,_ -> }
            )
        }
    }
}
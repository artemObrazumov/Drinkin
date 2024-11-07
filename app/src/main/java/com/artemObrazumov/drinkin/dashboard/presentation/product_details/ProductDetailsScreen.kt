package com.artemObrazumov.drinkin.dashboard.presentation.product_details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.dashboard.domain.models.CustomizableParameter
import com.artemObrazumov.drinkin.dashboard.domain.models.CustomizableParameterOption
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails
import com.artemObrazumov.drinkin.dashboard.presentation.models.ProductDetailsUi
import com.artemObrazumov.drinkin.dashboard.presentation.models.ProductUi
import com.artemObrazumov.drinkin.dashboard.presentation.models.toProductDetailsUi
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.components.ProductCard
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.components.ProductImage
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.PRODUCTS
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme

@Composable
fun ProductDetailsScreen(
    productDetailsUi: ProductDetailsUi,
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current

    var appearing by remember {
        mutableStateOf(true)
    }
    var animationDuration by remember {
        mutableIntStateOf(1000)
    }
    var circleRadius by remember {
        mutableStateOf(0.dp)
    }
    val circleRadiusAnimated by animateDpAsState(
        targetValue = circleRadius,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = EaseOutCubic
        ),
        label = "",
        finishedListener = {
            if (!appearing) {
                onGoBack()
            }
        }
    )
    var imageXOffset by remember {
        mutableStateOf(configuration.screenWidthDp.dp)
    }
    val imageXOffsetAnimated by animateDpAsState(
        targetValue = imageXOffset,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = EaseOutCubic
        ),
        label = ""
    )

    LaunchedEffect(appearing) {
        circleRadius = if (appearing) {
            configuration.screenWidthDp.dp
        } else {
            0.dp
        }

        imageXOffset = if (appearing) {
            0.dp
        } else {
            configuration.screenWidthDp.dp
        }

        animationDuration = if (appearing) {
            1000
        } else {
            400
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        item {
            ProductImage(
                imageRes = productDetailsUi.imageRes,
                circleRadius = circleRadiusAnimated,
                circleXOffset = (configuration.screenWidthDp + 8).dp,
                imageXOffset = imageXOffsetAnimated,
                name = productDetailsUi.name
            )
        }
        item {
            ProductCard(
                name = productDetailsUi.name,
                description = productDetailsUi.description,
                price = productDetailsUi.price,
                salePrice = productDetailsUi.salePrice,
                customizableParameters = productDetailsUi.customizableParams
            )
        }
    }

    BackHandler {
        appearing = false
    }
}

@PreviewLightDark
@Composable
fun ProductDetailsScreenPreview() {
    DrinkinTheme {
        Surface {
            ProductDetailsScreen(
                productDetailsUi = PRODUCT_DETAILS
            )
        }
    }
}

internal val PRODUCT_DETAILS = ProductDetails(
    id = 1,
    name = "Caramel Frappucino",
    price = 30f,
    salePrice = null,
    category = "AAA",
    imageRes = R.drawable.cup,
    description = "test description ".repeat(10),
    customizableParams = listOf(
        CustomizableParameter(
            name = "Size options",
            options = listOf(
                CustomizableParameterOption(
                    name = "Tall",
                    detail = "12 Fl Oz",
                    imageRes = R.drawable.cup_icon
                ),
                CustomizableParameterOption(
                    name = "Grande",
                    detail = "16 Fl Oz",
                    imageRes = R.drawable.cup_icon
                ),
                CustomizableParameterOption(
                    name = "Venti",
                    detail = "24 Fl Oz",
                    imageRes = R.drawable.cup_icon
                ),
                CustomizableParameterOption(
                    name = "Huge",
                    detail = "28 Fl Oz",
                    imageRes = R.drawable.cup_icon
                )
            )
        )
    )
).toProductDetailsUi("$")
package com.artemObrazumov.drinkin.dashboard.presentation.product_details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.presentation.LoadingScreenState
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT
import com.artemObrazumov.drinkin.dashboard.domain.models.CustomizableParameter
import com.artemObrazumov.drinkin.dashboard.domain.models.CustomizableParameterOption
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails
import com.artemObrazumov.drinkin.dashboard.presentation.models.ProductDetailsUi
import com.artemObrazumov.drinkin.dashboard.presentation.models.toProductDetailsUi
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.components.ProductCard
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.components.ProductImage
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.components.ProductOrderCard
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme

@Composable
fun ProductDetailsScreen(
    state: ProductDetailsScreenState,
    modifier: Modifier = Modifier,
    incrementCount: () -> Unit = {},
    decrementCount: () -> Unit = {},
    addToCart: () -> Unit = {},
    onParameterSelect: (parameter: String, optionIndex: Int) -> Unit = { _, _ -> },
    onGoBack: () -> Unit = {},
    menu: @Composable (onBackPress: () -> Unit) -> Unit = {}
) {
    var onBackPress by remember {
        mutableStateOf( onGoBack )
    }
    when (state) {
        ProductDetailsScreenState.Loading -> {
            LoadingScreenState(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                trackColor = Color.White
            )
        }

        is ProductDetailsScreenState.Content -> {
            ProductDetailsScreenContent(
                productDetailsUi = state.productDetailsUi,
                count = state.count,
                selectedParameters = state.selectedParameters,
                modifier = modifier,
                incrementCount = incrementCount,
                decrementCount = decrementCount,
                addToCart = addToCart,
                buttonState = state.buttonState,
                onParameterSelect = onParameterSelect,
                onGoBack = onGoBack,
                backPressHandler = { handler ->
                    onBackPress = handler
                }
            )
        }

        is ProductDetailsScreenState.Failure -> {

        }
    }
    menu(onBackPress)
}

@Composable
fun ProductDetailsScreenContent(
    productDetailsUi: ProductDetailsUi,
    count: Int,
    selectedParameters: Map<String, Int?>,
    buttonState: ButtonState,
    modifier: Modifier = Modifier,
    incrementCount: () -> Unit = {},
    decrementCount: () -> Unit = {},
    addToCart: () -> Unit = {},
    onParameterSelect: (parameter: String, optionIndex: Int) -> Unit = { _, _ -> },
    onGoBack: () -> Unit = {},
    backPressHandler: (handler: () -> Unit) -> Unit = {}
) {
    val orderCardHeight = 100.dp
    val orderCardHeightPadding = 16.dp
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
    var contentBlockScale by remember {
        mutableFloatStateOf(0f)
    }
    val contentBlockScaleAnimated by animateFloatAsState(
        targetValue = contentBlockScale,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = EaseOutCubic
        ),
        label = ""
    )
    var orderCardScale by remember {
        mutableFloatStateOf(0f)
    }
    val orderCardScaleAnimated by animateFloatAsState(
        targetValue = orderCardScale,
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

        contentBlockScale = if (appearing) {
            1f
        } else {
            0f
        }

        orderCardScale = if (appearing) {
            1f
        } else {
            0f
        }
    }

    val onBackPress = {
        appearing = false
    }

    BackHandler(onBack = onBackPress)
    backPressHandler(onBackPress)

    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            item {
                Spacer(
                    modifier = Modifier.height(52.dp)
                )
            }
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
                    price = productDetailsUi.basePrice,
                    salePrice = productDetailsUi.salePrice,
                    customizableParameters = productDetailsUi.customizableParams,
                    selectedParameters = selectedParameters,
                    onParameterSelect = { parameter, option ->
                        onParameterSelect(parameter, option)
                    },
                    modifier = Modifier
                        .graphicsLayer {
                            transformOrigin = TransformOrigin(0.5f, 0f)
                            scaleX = contentBlockScaleAnimated
                            scaleY = contentBlockScaleAnimated
                        }
                )
            }
            item {
                Spacer(
                    modifier = Modifier
                        .height(orderCardHeight)
                )
            }
        }

        ProductOrderCard(
            height = orderCardHeight,
            heightPadding = orderCardHeightPadding,
            count = count,
            buttonEnabled = (selectedParameters.size == productDetailsUi.customizableParams.size &&
                    count > 0),
            onSubtract = { decrementCount() },
            onAdd = { incrementCount() },
            onAddToCart = { addToCart() },
            buttonState = buttonState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 1f)
                    scaleX = orderCardScaleAnimated
                    scaleY = orderCardScaleAnimated
                }
        )
    }
}

@PreviewLightDark
@Composable
fun ProductDetailsScreenPreview() {
    DrinkinTheme {
        Surface {
            ProductDetailsScreenContent(
                productDetailsUi = PRODUCT_DETAILS,
                count = 10,
                selectedParameters = emptyMap(),
                buttonState = ButtonState.Idle
            )
        }
    }
}

internal val PRODUCT_DETAILS = ProductDetails(
    id = 1,
    name = "Caramel Frappucino",
    basePrice = 30f,
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
                    imageRes = R.drawable.cup_icon,
                    priceDifference = 0f
                ),
                CustomizableParameterOption(
                    name = "Grande",
                    detail = "16 Fl Oz",
                    imageRes = R.drawable.cup_icon,
                    priceDifference = 3f
                ),
                CustomizableParameterOption(
                    name = "Venti",
                    detail = "24 Fl Oz",
                    imageRes = R.drawable.cup_icon,
                    priceDifference = 5.5f
                ),
                CustomizableParameterOption(
                    name = "Huge",
                    detail = "28 Fl Oz",
                    imageRes = R.drawable.cup_icon,
                    priceDifference = 8.9f
                )
            )
        )
    )
).toProductDetailsUi(PRICE_UNIT)
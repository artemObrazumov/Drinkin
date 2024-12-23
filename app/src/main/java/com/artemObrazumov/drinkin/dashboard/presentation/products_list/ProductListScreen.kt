package com.artemObrazumov.drinkin.dashboard.presentation.products_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground
import com.artemObrazumov.drinkin.core.presentation.rememberIntOffsetSaver
import com.artemObrazumov.drinkin.core.presentation.rememberIntSizeSaver
import com.artemObrazumov.drinkin.core.presentation.rememberOffsetSaver
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT
import com.artemObrazumov.drinkin.dashboard.domain.models.Product
import com.artemObrazumov.drinkin.dashboard.presentation.models.CategoryUi
import com.artemObrazumov.drinkin.dashboard.presentation.models.ProductUi
import com.artemObrazumov.drinkin.dashboard.presentation.models.toProductUi
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.components.CategoriesList
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.components.ProductContent
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.components.ProductIndicator
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.components.ProductsPager
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.components.TransitionCircle
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.components.TransitionImage
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme
import kotlinx.coroutines.launch

internal enum class TransitionState {
    IDLE, TRANSITION, REVERSE
}

@Composable
fun ProductListScreen(
    state: ProductListScreenState,
    modifier: Modifier = Modifier,
    onDetailsScreen: () -> Unit = {},
    onCategoryClicked: (category: CategoryUi) -> Unit = {},
    menu: @Composable () -> Unit = {}
) {
    BeansBackground()
    when (state) {
        is ProductListScreenState.Loading -> {
            LoadingScreen()
        }

        is ProductListScreenState.Content -> {
            ProductListScreenContent(
                categories = state.categories,
                products = state.productsInList,
                selectedCategoryName = state.selectedCategoryName,
                modifier = modifier,
                onDetailsScreen = onDetailsScreen,
                onCategoryClicked = onCategoryClicked,
                menu = menu
            )
        }

        is ProductListScreenState.Failure -> {

        }
    }
}

@Composable
fun ProductListScreenContent(
    categories: List<CategoryUi>,
    products: List<ProductUi>,
    selectedCategoryName: String,
    modifier: Modifier = Modifier,
    onDetailsScreen: () -> Unit = {},
    onCategoryClicked: (category: CategoryUi) -> Unit = {},
    menu: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(
            pageCount = { products.size }
        )
        var transitionState by rememberSaveable {
            mutableStateOf(TransitionState.IDLE)
        }
        var transitionCenter by rememberSaveable(
            saver = rememberOffsetSaver()
        ) {
            mutableStateOf(Offset.Zero)
        }
        var transitionImageRes by rememberSaveable {
            mutableStateOf<Int?>(null)
        }
        var transitionImageSize by rememberSaveable(
            saver = rememberIntSizeSaver()
        ) {
            mutableStateOf(IntSize.Zero)
        }
        var transitionImagePosition by rememberSaveable(
            saver = rememberIntOffsetSaver()
        ) {
            mutableStateOf(IntOffset.Zero)
        }

        LaunchedEffect(true) {
            if (transitionState == TransitionState.TRANSITION) {
                transitionState = TransitionState.REVERSE
            }
        }
        Column {
            Spacer(modifier = Modifier.weight(1f))
            Box {
                val selectedCategoryIndex = categories
                    .indexOfFirst { it.name == selectedCategoryName }

                CategoriesList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            when (categories.size) {
                                4 -> 678.dp
                                else -> 728.dp
                            }
                        )
                        .align(Alignment.BottomCenter)
                        .graphicsLayer {
                            translationY = 0.dp.toPx()
                        },
                    categories = categories,
                    itemOffsetDp = when (categories.size) {
                        2 -> 56
                        3 -> 52
                        4 -> 48
                        else -> 56
                    },
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = MaterialTheme.colorScheme.tertiary,
                    selectedCategoryIndex = selectedCategoryIndex,
                    onCategoryClicked = {
                        scope.launch {
                            pagerState.scrollToPage(0)
                        }
                        onCategoryClicked(it)
                    }
                )
                ProductsPager(
                    modifier = Modifier
                        .height(516.dp)
                        .align(Alignment.BottomCenter),
                    pagerState = pagerState,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    itemsPaddingDp = 164,
                    onClick = { center, imageRes, imageSize, imagePosition ->
                        transitionCenter = center
                        transitionImageRes = imageRes
                        transitionImageSize = imageSize
                        transitionImagePosition = imagePosition
                        transitionState = TransitionState.TRANSITION
                    },
                    drinkItem = { page ->
                        products[page]
                    }
                )
                val currentDrink = products[pagerState.currentPage]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .padding(bottom = 24.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    ProductContent(
                        name = currentDrink.name,
                        price = currentDrink.price,
                        salePrice = currentDrink.salePrice
                    )
                    Spacer(
                        modifier = Modifier.height(28.dp)
                    )
                    ProductIndicator(
                        totalItems = products.size,
                        currentItem = pagerState.currentPage,
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                    )
                    Spacer(
                        modifier = Modifier.height(36.dp)
                    )
                }
            }
        }
        TransitionCircle(
            center = transitionCenter,
            isActive = transitionState == TransitionState.TRANSITION,
            onTransitionEnd = {
                when (transitionState) {
                    TransitionState.TRANSITION -> {
                        onDetailsScreen()
                    }

                    TransitionState.REVERSE -> {
                        transitionImageRes = null
                        transitionState = TransitionState.IDLE
                    }

                    else -> {}
                }
            }
        )
        if (transitionImageRes != null) {
            TransitionImage(
                imageRes = transitionImageRes!!,
                imageSize = transitionImageSize,
                imagePosition = transitionImagePosition,
                moveToTop = transitionState == TransitionState.TRANSITION
            )
        }
        menu()
    }
}

@PreviewLightDark
@Composable
fun DrinksListScreenPreview() {
    DrinkinTheme {
        Surface {
            ProductListScreenContent(
                categories = emptyList(),
                products = PRODUCTS,
                selectedCategoryName = ""
            )
        }
    }
}

internal val PRODUCTS = listOf(
    Product(
        id = 1,
        name = "Caramel Frappucino",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ).toProductUi(PRICE_UNIT),
    Product(
        id = 1,
        name = "Test drink 2",
        price = 30f,
        salePrice = 35f,
        category = "AAA",
        imageRes = R.drawable.cup
    ).toProductUi(PRICE_UNIT),
    Product(
        id = 1,
        name = "Test drink 3",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ).toProductUi(PRICE_UNIT),
    Product(
        id = 1,
        name = "Test drink 4",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ).toProductUi(PRICE_UNIT)
)
package com.artemObrazumov.drinkin.dashboard.presentation.products_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.dashboard.domain.models.Product
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.components.ProductContent
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.components.ProductsPager
import com.artemObrazumov.drinkin.dashboard.presentation.models.ProductUi
import com.artemObrazumov.drinkin.dashboard.presentation.models.toProductUi
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.components.ProductIndicator
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrinksListScreen(
    drinks: List<ProductUi>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { drinks.size })

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.weight(1f))
        Box {
            ProductsPager(
                modifier = Modifier
                    .fillMaxHeight(0.65f),
                pagerState = pagerState,
                backgroundColor = MaterialTheme.colorScheme.primary,
                itemsPaddingDp = 164,
                drinkItem = { page ->
                    drinks[page]
                }
            )
            val currentDrink = drinks[pagerState.currentPage]
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
                    modifier = Modifier.height(36.dp)
                )
                ProductIndicator(
                    totalItems = drinks.size,
                    currentItem = pagerState.currentPage,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun DrinksListScreenPreview() {
    DrinkinTheme {
        Surface {
            DrinksListScreen(
                drinks = PRODUCTS
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
    ).toProductUi("$"),
    Product(
        id = 1,
        name = "Test drink 2",
        price = 30f,
        salePrice = 35f,
        category = "AAA",
        imageRes = R.drawable.cup
    ).toProductUi("$"),
    Product(
        id = 1,
        name = "Test drink 3",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ).toProductUi("$"),
    Product(
        id = 1,
        name = "Test drink 4",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ).toProductUi("$")
)
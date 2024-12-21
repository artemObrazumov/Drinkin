package com.artemObrazumov.drinkin.dashboard.presentation.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductInCart
import com.artemObrazumov.drinkin.dashboard.presentation.cart.components.ProductInCartItem

@Composable
fun CartScreen(
    state: CartScreenState,
    modifier: Modifier = Modifier,
    menu: @Composable () -> Unit = {}
) {
    val st = state as CartScreenState.Content
    CartScreenContent(
        modifier = modifier,
        products = st.products
    )

    menu()
}

@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    products: List<ProductInCart>
) {
    LazyColumn(
        modifier = modifier
            .padding(top = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = products
        ) { product ->
            ProductInCartItem(
                product = product
            )
        }
    }
}
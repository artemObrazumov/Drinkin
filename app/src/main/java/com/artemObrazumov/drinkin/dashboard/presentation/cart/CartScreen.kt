package com.artemObrazumov.drinkin.dashboard.presentation.cart

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductInCart
import com.artemObrazumov.drinkin.dashboard.presentation.cart.components.EmptyCartMessage
import com.artemObrazumov.drinkin.dashboard.presentation.cart.components.ProductInCartDetailsScreen
import com.artemObrazumov.drinkin.dashboard.presentation.cart.components.ProductInCartItem
import com.artemObrazumov.drinkin.ui.theme.darkTextColor

@Composable
fun CartScreen(
    state: CartScreenState,
    modifier: Modifier = Modifier,
    menu: @Composable () -> Unit = {},
    onIncrementProduct: (id: Int) -> Unit,
    onDecrementProduct: (id: Int) -> Unit,
    onRemoveProduct: (id: Int) -> Unit,
    onHideDetails: () -> Unit,
    onViewProductDetails: (parameters: Map<String, String>) -> Unit,
) {
    CartScreenContent(
        modifier = modifier,
        products = state.products,
        showProductDetails = state.showProductDetails,
        productDetails = state.productDetails,
        onIncrementProduct = onIncrementProduct,
        onDecrementProduct = onDecrementProduct,
        onRemoveProduct = onRemoveProduct,
        onHideDetails = onHideDetails,
        onViewProductDetails = onViewProductDetails,
    )

    menu()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    products: List<ProductInCart>,
    showProductDetails: Boolean,
    productDetails: List<String>,
    onIncrementProduct: (id: Int) -> Unit,
    onDecrementProduct: (id: Int) -> Unit,
    onRemoveProduct: (id: Int) -> Unit,
    onHideDetails: () -> Unit,
    onViewProductDetails: (parameters: Map<String, String>) -> Unit,
) {
    if (products.isEmpty()) {
        EmptyCartMessage()
    }
    LazyColumn(
        modifier = modifier
            .padding(top = 106.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = products,
            key = { it.id }
        ) { product ->
            ProductInCartItem(
                modifier = Modifier.animateItem(),
                product = product,
                onIncrement = { onIncrementProduct(product.id) },
                onDecrement = { onDecrementProduct(product.id) },
                onRemove = { onRemoveProduct(product.id) },
                onViewDetails = { onViewProductDetails(product.parameters) }
            )
        }
    }
    if (showProductDetails) {
        ModalBottomSheet(onDismissRequest = onHideDetails) {
            ProductInCartDetailsScreen(details = productDetails)
        }
    }
}
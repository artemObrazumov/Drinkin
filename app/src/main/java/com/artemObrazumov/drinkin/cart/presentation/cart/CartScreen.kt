package com.artemObrazumov.drinkin.cart.presentation.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.address.domain.models.Address
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground
import com.artemObrazumov.drinkin.cart.domain.models.ProductInCart
import com.artemObrazumov.drinkin.cart.presentation.cart.components.EmptyCartMessage
import com.artemObrazumov.drinkin.cart.presentation.cart.components.MakeOrderCard
import com.artemObrazumov.drinkin.cart.presentation.cart.components.ProductInCartDetailsScreen
import com.artemObrazumov.drinkin.cart.presentation.cart.components.ProductInCartItem
import com.artemObrazumov.drinkin.product.presentation.product_details.components.ProductOrderCard

@Composable
fun CartScreen(
    state: CartScreenState,
    modifier: Modifier = Modifier,
    menu: @Composable () -> Unit = {},
    onAddressClicked: () -> Unit,
    onIncrementProduct: (id: Int) -> Unit,
    onDecrementProduct: (id: Int) -> Unit,
    onRemoveProduct: (id: Int) -> Unit,
    onHideDetails: () -> Unit,
    onViewProductDetails: (parameters: Map<String, String>) -> Unit,
) {
    BeansBackground()
    if (state.isLoading) {
        LoadingScreen(modifier = modifier)
    } else {
        CartScreenContent(
            modifier = modifier,
            products = state.products,
            showProductDetails = state.showProductDetails,
            productDetails = state.productDetails,
            address = state.address,
            onAddressClicked = onAddressClicked,
            onIncrementProduct = onIncrementProduct,
            onDecrementProduct = onDecrementProduct,
            onRemoveProduct = onRemoveProduct,
            onHideDetails = onHideDetails,
            onViewProductDetails = onViewProductDetails,
        )
    }

    menu()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    products: List<ProductInCart>,
    showProductDetails: Boolean,
    productDetails: List<String>,
    address: Address?,
    onAddressClicked: () -> Unit,
    onIncrementProduct: (id: Int) -> Unit,
    onDecrementProduct: (id: Int) -> Unit,
    onRemoveProduct: (id: Int) -> Unit,
    onHideDetails: () -> Unit,
    onViewProductDetails: (parameters: Map<String, String>) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val orderCardHeightPadding = 16.dp
        if (products.isEmpty()) {
            EmptyCartMessage()
        } else {
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
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(IntrinsicSize.Min),
            visible = products.isNotEmpty(),
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
        ) {
            MakeOrderCard(
                heightPadding = orderCardHeightPadding,
                address = address?.address ?: "Address not selected",
                buttonEnabled = address != null,
                onAddressSelectClicked = onAddressClicked,
                onMakeOrderClicked = {}
            )
        }
    }
}
package com.artemObrazumov.drinkin.order.presentation.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.app.ui.theme.DrinkinTheme
import com.artemObrazumov.drinkin.cart.domain.models.ProductInCart
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground
import com.artemObrazumov.drinkin.order.domain.models.Order
import com.artemObrazumov.drinkin.order.domain.models.OrderStatus
import com.artemObrazumov.drinkin.order.domain.models.toOrderItem
import com.artemObrazumov.drinkin.order.domain.models.toProductInOrder
import com.artemObrazumov.drinkin.order.presentation.models.OrderItemUi
import com.artemObrazumov.drinkin.order.presentation.models.toOrderItemUi
import com.artemObrazumov.drinkin.order.presentation.orders.components.EmptyOrdersListMessage
import com.artemObrazumov.drinkin.order.presentation.orders.components.OrderItem
import java.time.LocalDateTime

@Composable
fun OrdersScreen(
    state: OrdersScreenState,
    modifier: Modifier = Modifier,
    onOrderClicked: (orderId: Int) -> Unit = {},
    menu: @Composable () -> Unit = {},
) {
    BeansBackground()
    when (state) {
        OrdersScreenState.Loading -> {
            LoadingScreen(modifier = modifier)
        }
        
        is OrdersScreenState.Content -> {
            OrdersScreenContent(
                orders = state.orders,
                modifier = modifier
            )
        }

        is OrdersScreenState.Failure -> {

        }
    }

    menu()
}

@Composable
fun OrdersScreenContent(
    orders: List<OrderItemUi>,
    modifier: Modifier = Modifier
) {
    if (orders.isEmpty()) {
        EmptyOrdersListMessage()
    } else {
        LazyColumn(
            modifier = modifier
                .padding(top = 106.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(
                items = orders,
                key = { it.id }
            ) { order ->
                OrderItem(
                    order = order
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun OrdersScreenPreview() {
    DrinkinTheme {
        OrdersScreen(
            state = OrdersScreenState.Content(
                orders = ORDERS.map { it.toOrderItem().toOrderItemUi() }
            )
        )
    }
}

internal val PRODUCTS_IN_CART = listOf(
    ProductInCart(
        id = 1,
        productId = 1,
        name = "Test product",
        price = 3.1f,
        quantity = 1,
        imageRes = R.drawable.cup,
        parameters = emptyMap()
    ),
    ProductInCart(
        id = 2,
        productId = 2,
        name = "Test product 2",
        price = 7.0f,
        quantity = 2,
        imageRes = R.drawable.cup,
        parameters = emptyMap()
    )
)

internal val ORDERS = listOf(
    Order(
        id = 1,
        number = 123,
        products = PRODUCTS_IN_CART.map { it.toProductInOrder() },
        address = "test address",
        totalPrice = PRODUCTS_IN_CART.sumOf { it.price.toDouble() }.toFloat(),
        status = OrderStatus.IN_PROCESS,
        time = LocalDateTime.now()
    ),
    Order(
        id = 2,
        number = 234,
        products = PRODUCTS_IN_CART.map { it.toProductInOrder() },
        address = "test address 2",
        totalPrice = PRODUCTS_IN_CART.sumOf { it.price.toDouble() }.toFloat(),
        status = OrderStatus.READY,
        time = LocalDateTime.now().minusMinutes(5)
    )
)

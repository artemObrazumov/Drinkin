package com.artemObrazumov.drinkin.order.presentation.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground
import com.artemObrazumov.drinkin.order.presentation.models.OrderItemUi
import com.artemObrazumov.drinkin.order.presentation.orders.components.OrderItem

@Composable
fun OrdersScreen(
    state: OrdersScreenState,
    modifier: Modifier = Modifier,
    onOrderClicked: (orderId: Int) -> Unit,
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

@PreviewLightDark
@Composable
fun OrdersScreenPreview() {
    
}
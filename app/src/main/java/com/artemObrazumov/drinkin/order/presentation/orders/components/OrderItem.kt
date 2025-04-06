package com.artemObrazumov.drinkin.order.presentation.orders.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.artemObrazumov.drinkin.app.ui.theme.DrinkinTheme
import com.artemObrazumov.drinkin.order.domain.models.OrderItem
import com.artemObrazumov.drinkin.order.domain.models.OrderStatus
import com.artemObrazumov.drinkin.order.presentation.models.OrderItemUi
import com.artemObrazumov.drinkin.order.presentation.models.toOrderItemUi
import java.time.LocalDateTime

@Composable
fun OrderItem(
    order: OrderItemUi,
    modifier: Modifier = Modifier,
    onOrderClicked: () -> Unit = {}
) {

}

@PreviewLightDark
@Composable
fun OrderItemPreview() {
    DrinkinTheme {
        OrderItem(
            order = ORDER_ITEM
        )
    }
}

internal val ORDER_ITEM = OrderItem(
    id = 1,
    address = "test address",
    status = OrderStatus.IN_PROCESS,
    time = LocalDateTime.now()
).toOrderItemUi()

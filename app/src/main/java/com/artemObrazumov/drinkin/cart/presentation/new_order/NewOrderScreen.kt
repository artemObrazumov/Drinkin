package com.artemObrazumov.drinkin.cart.presentation.new_order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.address.domain.models.Address
import com.artemObrazumov.drinkin.cart.domain.models.Order
import com.artemObrazumov.drinkin.cart.domain.models.ProductInOrder
import com.artemObrazumov.drinkin.cart.presentation.models.OrderUi
import com.artemObrazumov.drinkin.cart.presentation.models.toOrderUi
import com.artemObrazumov.drinkin.cart.presentation.new_order.components.ProductInOrderItem
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground

@Composable
fun NewOrderScreen(
    state: NewOrderScreenState,
    modifier: Modifier = Modifier,
    menu: @Composable () -> Unit = {},
) {
    BeansBackground()
    when (state) {
        NewOrderScreenState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is NewOrderScreenState.Content -> {
            NewOrderScreenContent(
                order = state.order
            )
        }

        is NewOrderScreenState.Failure -> {

        }
    }

    menu()
}

@Composable
fun NewOrderScreenContent(
    order: OrderUi,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 106.dp)
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Products",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
            items(
                items = order.products,
                key = { it.id }
            ) { product ->
                ProductInOrderItem(
                    product = product
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun NewOrderScreenPreview() {
    NewOrderScreen(
        state = NewOrderScreenState.Content(
            order = ORDER,
            address = ADDRESS
        )
    )
}

internal val ORDER = Order(
    id = 1,
    products = listOf(
        ProductInOrder(
            id = 1,
            productId = 1,
            name = "test",
            price = 140f,
            quantity = 1,
            imageRes = R.drawable.cup,
            mapOf(
                "Size options" to "Grande",
                "Size options2" to "Grande",
                "Size options3" to "Grande",
            )
        ),
        ProductInOrder(
            id = 2,
            productId = 1,
            name = "test",
            price = 14f,
            quantity = 1,
            imageRes = R.drawable.cup,
            mapOf(
                "Size options" to "Grande",
                "Size options2" to "Grande",
                "Size options3" to "Grande",
            )
        ),
        ProductInOrder(
            id = 3,
            productId = 1,
            name = "test",
            price = 14f,
            quantity = 1,
            imageRes = R.drawable.cup,
            mapOf(
                "Size options" to "Grande",
                "Size options2" to "Grande",
                "Size options3" to "Grande",
            )
        )
    )
).toOrderUi()
internal val ADDRESS = Address(
    cafeId = 1,
    latitude = 10.0,
    longitude = 15.0,
    address = "test address"
)

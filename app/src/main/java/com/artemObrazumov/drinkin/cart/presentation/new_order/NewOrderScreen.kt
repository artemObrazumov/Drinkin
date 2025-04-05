package com.artemObrazumov.drinkin.cart.presentation.new_order

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground

@Composable
fun NewOrderScreen(
    state: NewOrderScreenState,
    modifier: Modifier = Modifier,
    onPaymentClicked: () -> Unit = {},
    onPaymentFinished: () -> Unit = {},
    menu: @Composable (canReturn: Boolean) -> Unit = {},
) {
    BeansBackground()
    when (state) {
        NewOrderScreenState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is NewOrderScreenState.Content -> {
            LaunchedEffect(state.paymentFinished) {
                if (state.paymentFinished) {
                    onPaymentFinished()
                }
            }
            NewOrderScreenContent(
                order = state.order,
                orderPaymentState = state.orderPaymentState,
                onPaymentClicked = onPaymentClicked
            )
            BackHandler(enabled = state.orderPaymentState.loading) {}
        }

        is NewOrderScreenState.Failure -> {

        }
    }

    val canReturn = if (state is NewOrderScreenState.Content) {
        !state.orderPaymentState.loading
    } else {
        true
    }
    menu(canReturn)
}

@Composable
fun NewOrderScreenContent(
    order: OrderUi,
    orderPaymentState: OrderPaymentState,
    modifier: Modifier = Modifier,
    onPaymentClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(top = 106.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Place,
                    contentDescription = stringResource(R.string.address),
                    tint = MaterialTheme.colorScheme.tertiaryContainer
                )
                Spacer(
                    modifier = Modifier
                        .width(4.dp)
                )
                Text(
                    text = "Address",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Text(
                text = order.address,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(R.drawable.dollar),
                    contentDescription = stringResource(R.string.address),
                    tint = MaterialTheme.colorScheme.tertiaryContainer
                )
                Spacer(
                    modifier = Modifier
                        .width(4.dp)
                )
                Text(
                    text = "Price",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Text(
                text = order.totalPrice.formatted,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onPaymentClicked
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (orderPaymentState.loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        painterResource(R.drawable.credit_card),
                        contentDescription = stringResource(R.string.address)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(8.dp)
                    )
                    Text(
                        text = stringResource(R.string.go_to_payment),
                        color = Color.White
                    )
                }
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
            address = ADDRESS,
            orderPaymentState = OrderPaymentState()
        )
    )
}

@PreviewLightDark
@Composable
fun NewOrderScreenPreviewError() {
    NewOrderScreen(
        state = NewOrderScreenState.Failure(NetworkError.UNKNOWN_ERROR)
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
    ),
    address = "address, 2"
).toOrderUi()
internal val ADDRESS = Address(
    cafeId = 1,
    latitude = 10.0,
    longitude = 15.0,
    address = "test address"
)

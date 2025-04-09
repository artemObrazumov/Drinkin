package com.artemObrazumov.drinkin.order.presentation.order_details

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.app.ui.theme.DrinkinTheme
import com.artemObrazumov.drinkin.app.ui.theme.blendTextColor
import com.artemObrazumov.drinkin.app.ui.theme.darkTextColor
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground
import com.artemObrazumov.drinkin.core.presentation.components.OutlinedBlock
import com.artemObrazumov.drinkin.core.presentation.resolve
import com.artemObrazumov.drinkin.order.presentation.componenets.ProductInOrderItem
import com.artemObrazumov.drinkin.order.presentation.models.OrderUi
import com.artemObrazumov.drinkin.order.presentation.models.toOrderUi
import com.artemObrazumov.drinkin.order.presentation.orders.ORDERS

@Composable
fun OrderDetailsScreen(
    state: OrderDetailsScreenState,
    modifier: Modifier = Modifier,
    menu: @Composable () -> Unit = {},
) {
    BeansBackground()
    when (state) {
        is OrderDetailsScreenState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is OrderDetailsScreenState.Content -> {
            OrderDetailsScreenContent(
                order = state.order,
                modifier = modifier
            )
        }

        is OrderDetailsScreenState.Failure -> {

        }
    }

    menu()
}

@Composable
fun OrderDetailsScreenContent(
    order: OrderUi,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 106.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        val orderNumberString = stringResource(R.string.order_number, order.number)
        val statusString = order.status.resolve(LocalContext.current)
        OutlinedBlock(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = orderNumberString,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                color = blendTextColor
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
            Text(
                text = order.time,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = darkTextColor
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
            Text(
                text = statusString,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                color = darkTextColor
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                    .height(4.dp)
            )
            Text(
                text = order.address,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                    .height(4.dp)
            )
            Text(
                text = order.totalPrice.formatted,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            )
        }

        OutlinedBlock {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
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
}

@PreviewLightDark
@Composable
fun OrderDetailsScreenPreview(modifier: Modifier = Modifier) {
    DrinkinTheme {
        OrderDetailsScreen(
            state = OrderDetailsScreenState.Content(
                order = ORDERS.first().toOrderUi()
            )
        )
    }
}
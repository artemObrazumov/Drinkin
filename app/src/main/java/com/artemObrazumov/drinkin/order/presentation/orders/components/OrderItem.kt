package com.artemObrazumov.drinkin.order.presentation.orders.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
import com.artemObrazumov.drinkin.core.presentation.resolve
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
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .clickable(
                onClick = onOrderClicked
            )
    ) {
        Column {
            val orderNumberString = stringResource(R.string.order_number, order.number)
            val statusString = order.status.resolve(LocalContext.current)
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
                    .height(8.dp)
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
                    .height(8.dp)
            )
            Text(
                text = statusString,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                ),
                color = darkTextColor
            )
        }
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = order.price.formatted,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = darkTextColor
            )
        }
    }
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
    number = 123,
    address = "test address",
    status = OrderStatus.IN_PROCESS,
    time = LocalDateTime.now(),
    price = 15.0f
).toOrderItemUi()

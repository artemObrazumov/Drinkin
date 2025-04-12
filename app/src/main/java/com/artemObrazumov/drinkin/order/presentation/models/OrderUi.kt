package com.artemObrazumov.drinkin.order.presentation.models

import com.artemObrazumov.drinkin.core.data.Resource
import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT
import com.artemObrazumov.drinkin.order.domain.models.Order
import com.artemObrazumov.drinkin.order.domain.models.OrderStatus
import com.artemObrazumov.drinkin.order.domain.models.asStringResource
import java.time.format.DateTimeFormatter

data class OrderUi(
    val id: Int,
    val number: Int,
    val products: List<ProductInOrderUi>,
    val address: String,
    val totalPrice: FormattedValue<Float>,
    val status: Resource.StringResource,
    val time: String
)

fun Order.toOrderUi(): OrderUi {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")
    return OrderUi(
        id = id,
        number = number,
        products = products.map {
            it.toProductInOrderUi()
        },
        address = address,
        totalPrice = totalPrice.asFormattedPrice(PRICE_UNIT),
        status = status.asStringResource(),
        time = formatter.format(this.time)
    )
}

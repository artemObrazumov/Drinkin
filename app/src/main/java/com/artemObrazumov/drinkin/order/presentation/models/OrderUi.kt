package com.artemObrazumov.drinkin.order.presentation.models

import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT
import com.artemObrazumov.drinkin.order.domain.models.Order
import com.artemObrazumov.drinkin.order.domain.models.OrderStatus

data class OrderUi(
    val id: Int,
    val products: List<ProductInOrderUi>,
    val address: String,
    val totalPrice: FormattedValue<Float>,
    val status: OrderStatus
)

fun Order.toOrderUi(): OrderUi {
    return OrderUi(
        id = id,
        products = products.map {
            it.toProductInOrderUi()
        },
        address = address,
        totalPrice = totalPrice.asFormattedPrice(PRICE_UNIT),
        status = status
    )
}

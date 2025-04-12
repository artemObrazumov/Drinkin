package com.artemObrazumov.drinkin.order.presentation.models

import com.artemObrazumov.drinkin.core.data.Resource
import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT
import com.artemObrazumov.drinkin.order.domain.models.OrderItem
import com.artemObrazumov.drinkin.order.domain.models.asStringResource
import java.time.format.DateTimeFormatter

data class OrderItemUi(
    val id: Int,
    val number: Int,
    val address: String,
    val status: Resource.StringResource,
    val time: String,
    val price: FormattedValue<Float>
)

fun OrderItem.toOrderItemUi(): OrderItemUi {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")
    return OrderItemUi(
        id = id,
        number = number,
        address = address,
        status = status.asStringResource(),
        time = time.format(formatter),
        price = price.asFormattedPrice(PRICE_UNIT)
    )
}
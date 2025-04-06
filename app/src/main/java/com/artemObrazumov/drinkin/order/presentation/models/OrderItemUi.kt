package com.artemObrazumov.drinkin.order.presentation.models

import com.artemObrazumov.drinkin.core.data.Resource
import com.artemObrazumov.drinkin.order.domain.models.OrderItem
import com.artemObrazumov.drinkin.order.domain.models.asStringResource
import java.time.format.DateTimeFormatter

data class OrderItemUi(
    val id: Int,
    val address: String,
    val status: Resource.StringResource,
    val time: String
)

fun OrderItem.toOrderItemUi(): OrderItemUi {
    val formatter = DateTimeFormatter.ofPattern("dd M yyyy HH:mm")
    return OrderItemUi(
        id = id,
        address = address,
        status = status.asStringResource(),
        time = time.format(formatter)
    )
}
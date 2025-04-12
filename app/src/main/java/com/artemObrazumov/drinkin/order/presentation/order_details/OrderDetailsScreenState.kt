package com.artemObrazumov.drinkin.order.presentation.order_details

import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.order.presentation.models.OrderUi

sealed class OrderDetailsScreenState {

    data object Loading: OrderDetailsScreenState()
    data class Content(
        val order: OrderUi
    ): OrderDetailsScreenState()
    data class Failure(
        val error: Error
    ): OrderDetailsScreenState()
}
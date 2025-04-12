package com.artemObrazumov.drinkin.order.presentation.new_order

import com.artemObrazumov.drinkin.address.domain.models.Address
import com.artemObrazumov.drinkin.order.presentation.models.DraftOrderUi
import com.artemObrazumov.drinkin.core.domain.util.Error

sealed class NewOrderScreenState {
    data object Loading: NewOrderScreenState()
    data class Content(
        val order: DraftOrderUi,
        val address: Address,
        val orderPaymentState: OrderPaymentState,
        val paymentFinished: Boolean = false,
    ): NewOrderScreenState()
    data class Failure(
        val error: Error
    ): NewOrderScreenState()
}

data class OrderPaymentState (
    val loading: Boolean = false,
    val error: Error? = null
)

package com.artemObrazumov.drinkin.cart.presentation.new_order

import com.artemObrazumov.drinkin.address.domain.models.Address
import com.artemObrazumov.drinkin.cart.presentation.models.OrderUi
import com.artemObrazumov.drinkin.core.domain.util.Error

sealed class NewOrderScreenState {
    data object Loading: NewOrderScreenState()
    data class Content(
        val order: OrderUi,
        val address: Address
    ): NewOrderScreenState()
    data class Failure(
        val error: Error
    ): NewOrderScreenState()
}

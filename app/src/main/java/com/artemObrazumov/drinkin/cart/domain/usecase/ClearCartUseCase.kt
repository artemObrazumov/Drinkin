package com.artemObrazumov.drinkin.cart.domain.usecase

import com.artemObrazumov.drinkin.cart.domain.data_source.CartDataSource

class ClearCartUseCase (
    private val cartDataSource: CartDataSource
) {

    // TODO: process success and failure cases
    suspend operator fun invoke() {
        cartDataSource.clearCart()
    }
}
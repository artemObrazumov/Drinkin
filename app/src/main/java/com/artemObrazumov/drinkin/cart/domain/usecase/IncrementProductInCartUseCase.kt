package com.artemObrazumov.drinkin.cart.domain.usecase

import com.artemObrazumov.drinkin.cart.domain.data_source.CartDataSource

class IncrementProductInCartUseCase(
    private val dataSource: CartDataSource
) {

    suspend operator fun invoke(productId: Int) {
        dataSource.incrementProductInCart(productId)
    }
}
package com.artemObrazumov.drinkin.dashboard.domain.usecase.cart

import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource

class IncrementProductInCartUseCase(
    private val dataSource: ProductDataSource
) {

    suspend operator fun invoke(productId: Int) {
        dataSource.incrementProductInCart(productId)
    }
}
package com.artemObrazumov.drinkin.cart.domain.usecase

import com.artemObrazumov.drinkin.cart.domain.data_source.CartDataSource
import com.artemObrazumov.drinkin.cart.domain.models.ProductInCart

class GetProductsInCartUseCase(
    private val dataSource: CartDataSource
) {

    operator fun invoke(): List<ProductInCart> {
        return try {
            dataSource.productsInCartFlow.replayCache.first()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
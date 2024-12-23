package com.artemObrazumov.drinkin.cart.domain.usecase

import com.artemObrazumov.drinkin.cart.domain.data_source.CartDataSource
import com.artemObrazumov.drinkin.cart.domain.models.ProductInCart
import kotlinx.coroutines.flow.Flow

class GetProductsInCartFlowUseCase(
    private val dataSource: CartDataSource
) {

    operator fun invoke(): Flow<List<ProductInCart>> {
        return dataSource.productsInCartFlow
    }
}
package com.artemObrazumov.drinkin.dashboard.domain.usecase.cart

import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductInCart
import kotlinx.coroutines.flow.Flow

class GetProductsInCartFlowUseCase(
    private val dataSource: ProductDataSource
) {

    operator fun invoke(): Flow<List<ProductInCart>> {
        return dataSource.productsInCartFlow
    }
}
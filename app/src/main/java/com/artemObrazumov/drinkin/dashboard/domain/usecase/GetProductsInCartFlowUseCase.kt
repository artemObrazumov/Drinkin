package com.artemObrazumov.drinkin.dashboard.domain.usecase

import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductInCart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last

class GetProductsInCartFlowUseCase(
    private val dataSource: ProductDataSource
) {

    operator fun invoke(): Flow<List<ProductInCart>> {
        return dataSource.productsInCartFlow
    }
}
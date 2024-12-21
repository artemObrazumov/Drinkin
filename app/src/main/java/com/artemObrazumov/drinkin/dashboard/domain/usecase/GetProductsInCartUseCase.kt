package com.artemObrazumov.drinkin.dashboard.domain.usecase

import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductInCart
import kotlinx.coroutines.flow.last

class GetProductsInCartUseCase(
    private val dataSource: ProductDataSource
) {

    operator fun invoke(): List<ProductInCart> {
        return try {
            dataSource.productsInCartFlow.replayCache.first()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
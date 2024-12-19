package com.artemObrazumov.drinkin.dashboard.domain.usecase

import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource

class AddProductsToCartUseCase(
    private val dataSource: ProductDataSource
) {

    suspend operator fun invoke(
        productId: Int,
        count: Int,
        selectedParameters: Map<String, Int>,
    ): AddProductsToCartUseCaseResult {
        return when(val result = dataSource.addProductsToCart(productId, count, selectedParameters)) {
            is Result.Error -> {
                AddProductsToCartUseCaseResult.Failure(result.error)
            }
            is Result.Success -> {
                AddProductsToCartUseCaseResult.Success
            }
        }
    }
}

sealed class AddProductsToCartUseCaseResult {

    data class Failure(
        val error: Error
    ) : AddProductsToCartUseCaseResult()

    data object Success: AddProductsToCartUseCaseResult()
}
package com.artemObrazumov.drinkin.cart.domain.usecase

import com.artemObrazumov.drinkin.cart.domain.data_source.CartDataSource
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.product.domain.data_source.ProductDataSource

class AddProductsToCartUseCase(
    private val productDataSource: ProductDataSource,
    private val cartDataSource: CartDataSource
) {

    suspend operator fun invoke(
        productId: Int,
        count: Int,
        selectedParameters: Map<String, Int>,
    ): AddProductsToCartUseCaseResult {
        val productDetailsResult = productDataSource.getProductDetails(productId)
        if (productDetailsResult is Result.Error) {
            return AddProductsToCartUseCaseResult.Failure(productDetailsResult.error)
        }
        val productDetails = (productDetailsResult as Result.Success).data
        return when(
            val result = cartDataSource.addProductToCart(productDetails, count, selectedParameters)
        ) {
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
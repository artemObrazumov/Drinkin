package com.artemObrazumov.drinkin.dashboard.domain.usecase

import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails

class GetProductDetailUseCase(
    private val dataSource: ProductDataSource
) {

    suspend operator fun invoke(productId: Int): GetProductDetailUseCaseResult {
        return when (val result = dataSource.getProductDetails(productId)) {
            is Result.Success -> {
                GetProductDetailUseCaseResult.Success(result.data)
            }

            is Result.Error -> {
                GetProductDetailUseCaseResult.Failure(result.error)
            }
        }
    }
}

sealed class GetProductDetailUseCaseResult {

    data class Failure(
        val error: Error
    ) : GetProductDetailUseCaseResult()

    data class Success(
        val productDetails: ProductDetails
    ) : GetProductDetailUseCaseResult()
}
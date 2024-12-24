package com.artemObrazumov.drinkin.address.domain.usecase

import com.artemObrazumov.drinkin.address.domain.data_source.CafeAddressDataSource
import com.artemObrazumov.drinkin.address.domain.models.Cafe
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.product.domain.models.ProductDetails

class GetCafesUseCase(
    private val cafeAddressDataSource: CafeAddressDataSource
) {
    suspend operator fun invoke(): GetCafesUseCaseResult {
        return when(val result = cafeAddressDataSource.getCafes()) {
            is Result.Error -> {
                GetCafesUseCaseResult.Failure(result.error)
            }
            is Result.Success -> {
                GetCafesUseCaseResult.Success(result.data)
            }
        }
    }
}

sealed class GetCafesUseCaseResult {

    data class Failure(
        val error: Error
    ) : GetCafesUseCaseResult()

    data class Success(
        val cafes: List<Cafe>
    ) : GetCafesUseCaseResult()
}
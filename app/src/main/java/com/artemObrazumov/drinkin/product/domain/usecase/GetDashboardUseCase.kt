package com.artemObrazumov.drinkin.product.domain.usecase

import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.product.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.product.domain.models.Category
import com.artemObrazumov.drinkin.product.domain.models.Product
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class GetDashboardUseCase(
    private val dataSource: ProductDataSource
) {

    /*
    * Loads categories and products for dashboard. If any of requests failed, send
    * failure result. Otherwise send success result.
    */
    suspend operator fun invoke(): GetDashboardUseCaseResult {
        return coroutineScope {
            var categories: List<Category> = emptyList()
            var products: List<Product> = emptyList()
            delay(1000)
            listOf(
                launch {
                    when(val result = dataSource.getCategories()) {
                        is Result.Success -> {
                            categories = result.data
                        }
                        is Result.Error -> {
                            GetDashboardUseCaseResult.Failure(result.error)
                        }
                    }
                },

                launch {
                    when(val result = dataSource.getProducts()) {
                        is Result.Success -> {
                            products = result.data
                        }
                        is Result.Error -> {
                            GetDashboardUseCaseResult.Failure(result.error)
                        }
                    }
                }
            ).joinAll()

            GetDashboardUseCaseResult.Success(
                categories = categories,
                products = products
            )
        }
    }
}

sealed class GetDashboardUseCaseResult {

    data class Failure(
        val error: Error
    ): GetDashboardUseCaseResult()

    data class Success(
        val categories: List<Category>,
        val products: List<Product>
    ): GetDashboardUseCaseResult()
}
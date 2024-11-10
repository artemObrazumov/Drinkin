package com.artemObrazumov.drinkin.dashboard.domain.usecase

import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.onError
import com.artemObrazumov.drinkin.core.domain.util.onSuccess
import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.dashboard.domain.models.Category
import com.artemObrazumov.drinkin.dashboard.domain.models.Product
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
                    dataSource.getCategories()
                        .onSuccess { data ->
                            categories = data
                        }
                        .onError { error ->
                            GetDashboardUseCaseResult.Failure(error)
                        }
                },

                launch {
                    dataSource.getProducts()
                        .onSuccess { data ->
                            products = data
                        }
                        .onError { error ->
                            GetDashboardUseCaseResult.Failure(error)
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
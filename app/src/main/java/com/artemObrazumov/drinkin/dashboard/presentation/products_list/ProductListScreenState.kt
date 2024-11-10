package com.artemObrazumov.drinkin.dashboard.presentation.products_list

import androidx.compose.runtime.Immutable
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.dashboard.presentation.models.CategoryUi
import com.artemObrazumov.drinkin.dashboard.presentation.models.ProductUi

@Immutable
sealed class ProductListScreenState {

    data object Loading: ProductListScreenState()
    data class Content(
        val categories: List<CategoryUi> = emptyList(),
        val products: List<ProductUi> = emptyList()
    ): ProductListScreenState()
    data class Failure(
        val error: Error
    ): ProductListScreenState()
}
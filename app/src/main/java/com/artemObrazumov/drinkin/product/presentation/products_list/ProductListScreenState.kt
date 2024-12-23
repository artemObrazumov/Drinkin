package com.artemObrazumov.drinkin.product.presentation.products_list

import androidx.compose.runtime.Immutable
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.product.presentation.models.CategoryUi
import com.artemObrazumov.drinkin.product.presentation.models.ProductUi

@Immutable
sealed class ProductListScreenState {

    data object Loading: ProductListScreenState()
    data class Content(
        val categories: List<CategoryUi> = emptyList(),
        val allProducts: List<ProductUi> = emptyList(),
        val productsInList: List<ProductUi> = emptyList(),
        val selectedCategoryName: String = ""
    ): ProductListScreenState()
    data class Failure(
        val error: Error
    ): ProductListScreenState()
}
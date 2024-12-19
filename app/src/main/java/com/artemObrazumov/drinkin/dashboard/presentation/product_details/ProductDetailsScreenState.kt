package com.artemObrazumov.drinkin.dashboard.presentation.product_details

import androidx.compose.runtime.Immutable
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.dashboard.presentation.models.ProductDetailsUi

@Immutable
sealed class ProductDetailsScreenState {

    data object Loading: ProductDetailsScreenState()
    data class Content(
        val productDetailsUi: ProductDetailsUi,
        val count: Int,
        val selectedParameters: Map<String, Int>,
        val addingToCart: Boolean = false
    ): ProductDetailsScreenState()
    data class Failure(
        val error: Error
    ): ProductDetailsScreenState()
}
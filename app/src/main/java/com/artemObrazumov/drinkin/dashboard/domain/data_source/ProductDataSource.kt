package com.artemObrazumov.drinkin.dashboard.domain.data_source

import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.dashboard.domain.models.Category
import com.artemObrazumov.drinkin.dashboard.domain.models.Product
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductInCart
import kotlinx.coroutines.flow.SharedFlow

interface ProductDataSource {
    val productsInCartFlow: SharedFlow<List<ProductInCart>>

    suspend fun getProducts(): Result<List<Product>, NetworkError>
    suspend fun getCategories(): Result<List<Category>, NetworkError>
    suspend fun getProductDetails(productId: Int): Result<ProductDetails, NetworkError>
    suspend fun addProductsToCart(
        productId: Int,
        count: Int,
        selectedParameters: Map<String, Int>
    ): Result<Int, NetworkError>
    suspend fun incrementProductInCart(productId: Int)
    suspend fun decrementProductInCart(productId: Int)
    suspend fun removeProductFromCart(productId: Int)
}
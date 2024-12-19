package com.artemObrazumov.drinkin.dashboard.domain.data_source

import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.dashboard.domain.models.Category
import com.artemObrazumov.drinkin.dashboard.domain.models.Product
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails

interface ProductDataSource {
    suspend fun getProducts(): Result<List<Product>, NetworkError>
    suspend fun getCategories(): Result<List<Category>, NetworkError>
    suspend fun getProductDetails(productId: Int): Result<ProductDetails, NetworkError>
    suspend fun addProductsToCart(
        productId: Int,
        count: Int,
        selectedParameters: Map<String, Int>
    ): Result<Int, NetworkError>

}
package com.artemObrazumov.drinkin.product.domain.data_source

import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.product.domain.models.Category
import com.artemObrazumov.drinkin.product.domain.models.Product
import com.artemObrazumov.drinkin.product.domain.models.ProductDetails

interface ProductDataSource {
    suspend fun getProducts(): Result<List<Product>, NetworkError>
    suspend fun getCategories(): Result<List<Category>, NetworkError>
    suspend fun getProductDetails(productId: Int): Result<ProductDetails, NetworkError>
}
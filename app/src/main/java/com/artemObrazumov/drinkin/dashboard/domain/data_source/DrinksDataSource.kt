package com.artemObrazumov.drinkin.dashboard.domain.data_source

import com.artemObrazumov.drinkin.dashboard.domain.models.Product
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails

interface DrinksDataSource {
    suspend fun getDrinks(): List<Product>
    suspend fun getProductDetails(): ProductDetails
}
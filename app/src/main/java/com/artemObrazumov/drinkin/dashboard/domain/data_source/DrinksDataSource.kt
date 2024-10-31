package com.artemObrazumov.drinkin.dashboard.domain.data_source

import com.artemObrazumov.drinkin.dashboard.domain.models.Product

interface DrinksDataSource {
    suspend fun getDrinks(): List<Product>
}
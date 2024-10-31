package com.artemObrazumov.drinkin.dashboard.data.data_source

import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.dashboard.domain.data_source.DrinksDataSource
import com.artemObrazumov.drinkin.dashboard.domain.models.Product

class DrinksMockDataSource: DrinksDataSource {

    override suspend fun getDrinks(): List<Product> {
        return Products
    }
}

internal val Products = listOf(
    Product(
        id = 1,
        name = "Test drink",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    )
)
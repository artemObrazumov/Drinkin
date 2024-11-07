package com.artemObrazumov.drinkin.dashboard.data.data_source

import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.dashboard.domain.data_source.DrinksDataSource
import com.artemObrazumov.drinkin.dashboard.domain.models.Product
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails

class DrinksMockDataSource: DrinksDataSource {

    override suspend fun getDrinks(): List<Product> {
        return Products
    }

    override suspend fun getProductDetails(): ProductDetails {
        return PRODUCT_DETAILS.first()
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

internal val PRODUCT_DETAILS = listOf(
    ProductDetails(
        id = 1,
        name = "Test drink",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup,
        description = "test description ".repeat(10),
        customizableParams = listOf()
    )
)
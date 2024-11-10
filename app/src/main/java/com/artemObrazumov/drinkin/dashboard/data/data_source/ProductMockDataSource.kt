package com.artemObrazumov.drinkin.dashboard.data.data_source

import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.dashboard.domain.models.Category
import com.artemObrazumov.drinkin.dashboard.domain.models.Product
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails

class ProductMockDataSource: ProductDataSource {

    override suspend fun getProducts(): Result<List<Product>, NetworkError> {
        return Result.Success(Products)
    }

    override suspend fun getCategories(): Result<List<Category>, NetworkError> {
        return Result.Success(CATEGORIES)
    }

    override suspend fun getProductDetails(): Result<ProductDetails, NetworkError> {
        return Result.Success(PRODUCT_DETAILS.first())
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

internal val CATEGORIES = listOf(
    Category(
        name = "test",
        title = "Test",
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
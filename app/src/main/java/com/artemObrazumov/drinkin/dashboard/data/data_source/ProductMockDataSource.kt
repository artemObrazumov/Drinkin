package com.artemObrazumov.drinkin.dashboard.data.data_source

import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.dashboard.domain.models.Category
import com.artemObrazumov.drinkin.dashboard.domain.models.CustomizableParameter
import com.artemObrazumov.drinkin.dashboard.domain.models.CustomizableParameterOption
import com.artemObrazumov.drinkin.dashboard.domain.models.Product
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails
import kotlinx.coroutines.delay

class ProductMockDataSource : ProductDataSource {

    override suspend fun getProducts(): Result<List<Product>, NetworkError> {
        return Result.Success(Products)
    }

    override suspend fun getCategories(): Result<List<Category>, NetworkError> {
        return Result.Success(CATEGORIES)
    }

    override suspend fun getProductDetails(productId: Int): Result<ProductDetails, NetworkError> {
        return Result.Success(PRODUCT_DETAILS.first())
    }

    override suspend fun addProductsToCart(
        productId: Int,
        count: Int,
        selectedParameters: Map<String, Int>
    ): Result<Int, NetworkError> {
        return Result.Success(200)
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
    ),
    Product(
        id = 2,
        name = "Test drink",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 3,
        name = "Test drink",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 4,
        name = "Test drink",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 5,
        name = "Test drink",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 6,
        name = "Test drink",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 7,
        name = "Test drink",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 8,
        name = "Test drink",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 9,
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
        name = "Caramel Frappucino",
        price = 30f,
        salePrice = null,
        category = "AAA",
        imageRes = R.drawable.cup,
        description = "test description ".repeat(10),
        customizableParams = listOf(
            CustomizableParameter(
                name = "Size options",
                options = listOf(
                    CustomizableParameterOption(
                        name = "Tall",
                        detail = "12 Fl Oz",
                        imageRes = R.drawable.cup_icon
                    ),
                    CustomizableParameterOption(
                        name = "Grande",
                        detail = "16 Fl Oz",
                        imageRes = R.drawable.cup_icon
                    ),
                    CustomizableParameterOption(
                        name = "Venti",
                        detail = "24 Fl Oz",
                        imageRes = R.drawable.cup_icon
                    ),
                    CustomizableParameterOption(
                        name = "Huge",
                        detail = "28 Fl Oz",
                        imageRes = R.drawable.cup_icon
                    )
                )
            )
        )
    )
)
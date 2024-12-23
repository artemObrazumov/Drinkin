package com.artemObrazumov.drinkin.product.data.data_source

import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.product.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.product.domain.models.Category
import com.artemObrazumov.drinkin.product.domain.models.CustomizableParameter
import com.artemObrazumov.drinkin.product.domain.models.CustomizableParameterOption
import com.artemObrazumov.drinkin.product.domain.models.Product
import com.artemObrazumov.drinkin.product.domain.models.ProductDetails
import kotlinx.coroutines.delay

class ProductMockDataSource : ProductDataSource {

    override suspend fun getProducts(): Result<List<Product>, NetworkError> {
        return Result.Success(Products)
    }

    override suspend fun getCategories(): Result<List<Category>, NetworkError> {
        return Result.Success(CATEGORIES)
    }

    override suspend fun getProductDetails(productId: Int): Result<ProductDetails, NetworkError> {
        delay(500)
        return Result.Success(PRODUCT_DETAILS.first())
    }
}

internal val Products = listOf(
    Product(
        id = 1,
        name = "Test coffee 1",
        price = 30f,
        salePrice = null,
        category = "coffee",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 2,
        name = "Test coffee 2",
        price = 30f,
        salePrice = null,
        category = "coffee",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 3,
        name = "Test drink 1",
        price = 30f,
        salePrice = null,
        category = "drinks",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 4,
        name = "Test drink 2",
        price = 30f,
        salePrice = null,
        category = "drinks",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 5,
        name = "Test tea 1",
        price = 30f,
        salePrice = null,
        category = "teas",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 6,
        name = "Test tea 2",
        price = 30f,
        salePrice = null,
        category = "teas",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 7,
        name = "Test bakery 1",
        price = 30f,
        salePrice = null,
        category = "bakery",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 8,
        name = "Test bakery 2",
        price = 30f,
        salePrice = null,
        category = "bakery",
        imageRes = R.drawable.cup
    ),
    Product(
        id = 9,
        name = "Test bakery 3",
        price = 30f,
        salePrice = null,
        category = "bakery",
        imageRes = R.drawable.cup
    )
)

internal val CATEGORIES = listOf(
    Category(
        name = "coffee",
        title = "Hot coffee",
        imageRes = R.drawable.coffee_icon
    ),
    Category(
        name = "drinks",
        title = "Drinks",
        imageRes = R.drawable.drinks_icon
    ),
    Category(
        name = "teas",
        title = "Hot teas",
        imageRes = R.drawable.tea_icon
    ),
    Category(
        name = "bakery",
        title = "Bakery",
        imageRes = R.drawable.bakery_icon
    )
)

internal val PRODUCT_DETAILS = listOf(
    ProductDetails(
        id = 1,
        name = "Caramel Frappucino",
        basePrice = 30f,
        salePrice = null,
        category = "coffee",
        imageRes = R.drawable.cup,
        description = "test description ".repeat(10),
        customizableParams = listOf(
            CustomizableParameter(
                name = "Size options",
                options = listOf(
                    CustomizableParameterOption(
                        name = "Tall",
                        detail = "12 Fl Oz",
                        imageRes = R.drawable.cup_icon,
                        priceDifference = 0f
                    ),
                    CustomizableParameterOption(
                        name = "Grande",
                        detail = "16 Fl Oz",
                        imageRes = R.drawable.cup_icon,
                        priceDifference = 3f
                    ),
                    CustomizableParameterOption(
                        name = "Venti",
                        detail = "24 Fl Oz",
                        imageRes = R.drawable.cup_icon,
                        priceDifference = 5.5f
                    ),
                    CustomizableParameterOption(
                        name = "Huge",
                        detail = "28 Fl Oz",
                        imageRes = R.drawable.cup_icon,
                        priceDifference = 8.9f
                    )
                )
            )
        )
    )
)
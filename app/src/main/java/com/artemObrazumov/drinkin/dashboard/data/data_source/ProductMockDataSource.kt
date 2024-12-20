package com.artemObrazumov.drinkin.dashboard.data.data_source

import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.core.domain.util.contentOrNull
import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.dashboard.domain.models.Category
import com.artemObrazumov.drinkin.dashboard.domain.models.CustomizableParameter
import com.artemObrazumov.drinkin.dashboard.domain.models.CustomizableParameterOption
import com.artemObrazumov.drinkin.dashboard.domain.models.Product
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductDetails
import com.artemObrazumov.drinkin.dashboard.domain.models.ProductInCart
import kotlinx.coroutines.delay

class ProductMockDataSource : ProductDataSource {

    private val productsInCart: MutableList<ProductInCart> = mutableListOf()

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
        val productDetails = getProductDetails(productId).contentOrNull()
            ?: return Result.Error(NetworkError.SERVER_ERROR)

        var price = productDetails.basePrice
        val parameters = mutableMapOf<String, String>()
        selectedParameters.forEach { (parameter, option) ->
            parameters[parameter] = productDetails.customizableParams.first { it.name == parameter }
                .options[option].name
        }
        val productInCart = ProductInCart(
            id = productId,
            name = productDetails.name,
            price = price,
            quantity = count,
            imageRes = productDetails.imageRes,
            parameters = parameters
        )
        productsInCart.add(productInCart)
        delay(800)
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
        basePrice = 30f,
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
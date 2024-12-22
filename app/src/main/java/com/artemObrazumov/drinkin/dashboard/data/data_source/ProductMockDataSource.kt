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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class ProductMockDataSource : ProductDataSource {

    private val productsInCart: MutableList<ProductInCart> = mutableListOf()
    private val _productsInCart = MutableSharedFlow<List<ProductInCart>>(
        replay = 1,
        extraBufferCapacity = 1
    )
    override val productsInCartFlow = _productsInCart.asSharedFlow()

    override suspend fun getProducts(): Result<List<Product>, NetworkError> {
        //delay(1000)
        return Result.Success(Products)
    }

    override suspend fun getCategories(): Result<List<Category>, NetworkError> {
        return Result.Success(CATEGORIES)
    }

    override suspend fun getProductDetails(productId: Int): Result<ProductDetails, NetworkError> {
        delay(500)
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
            val parameterOption = productDetails.customizableParams.first { it.name == parameter }
                .options[option]
            parameters[parameter] = parameterOption.name
            price += parameterOption.priceDifference
        }
        val productInCart = ProductInCart(
            id = Random(System.currentTimeMillis()).nextInt(),
            productId = productId,
            name = productDetails.name,
            price = price * count,
            quantity = count,
            imageRes = productDetails.imageRes,
            parameters = parameters
        )
        productsInCart.add(productInCart)
        _productsInCart.emit(productsInCart)
        return Result.Success(200)
    }

    override suspend fun incrementProductInCart(productId: Int) {
        val productIndex = productsInCart.indexOfFirst { it.id == productId }
        val productInCart = productsInCart.first { it.id == productId }
        val newQuantity = productInCart.quantity + 1
        val newPrice = (productInCart.price / productInCart.quantity) * newQuantity
        productsInCart[productIndex] = productInCart.copy(
            quantity = newQuantity,
            price = newPrice
        )
        _productsInCart.emit(productsInCart)
    }

    override suspend fun decrementProductInCart(productId: Int) {
        val productIndex = productsInCart.indexOfFirst { it.id == productId }
        val productInCart = productsInCart.first { it.id == productId }
        if (productInCart.quantity <= 1) {
            removeProductFromCart(productId)
            return
        }
        val newQuantity = productInCart.quantity - 1
        val newPrice = (productInCart.price / productInCart.quantity) * newQuantity
        productsInCart[productIndex] = productInCart.copy(
            quantity = newQuantity,
            price = newPrice
        )
        _productsInCart.emit(productsInCart)
    }

    override suspend fun removeProductFromCart(productId: Int) {
        val index = productsInCart.indexOfFirst { it.id == productId }
        productsInCart.removeAt(index)
        _productsInCart.emit(productsInCart)
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
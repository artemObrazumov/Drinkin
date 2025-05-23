package com.artemObrazumov.drinkin.cart.data

import com.artemObrazumov.drinkin.cart.domain.data_source.CartDataSource
import com.artemObrazumov.drinkin.cart.domain.models.ProductInCart
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.product.data.data_source.PRODUCT_DETAILS
import com.artemObrazumov.drinkin.product.domain.models.ProductDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class CartMockDataSource: CartDataSource {

    private val cartScope = CoroutineScope(SupervisorJob())

    private val productsInCart: MutableList<ProductInCart> = mutableListOf()
    private val _productsInCart = MutableSharedFlow<List<ProductInCart>>(
        replay = 1,
        extraBufferCapacity = 1
    ).also { flow ->
        cartScope.launch {
            flow.emit(productsInCart)
        }
    }
    private val productsInCartFlow = _productsInCart.asSharedFlow()

    // TODO: remove this script to add products to cart
    init {
        runBlocking {
            addProductToCart(
                PRODUCT_DETAILS.first(),
                1,
                selectedParameters = emptyMap()
            )
        }
    }

    override suspend fun addProductToCart(
        productDetails: ProductDetails,
        count: Int,
        selectedParameters: Map<String, Int>,
    ): Result<Int, NetworkError> {
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
            productId = productDetails.id,
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

    override fun getProductsInCartFlow(): SharedFlow<List<ProductInCart>> {
        return productsInCartFlow
    }

    override suspend fun clearCart() {
        productsInCart.clear()
        _productsInCart.emit(productsInCart)
    }
}
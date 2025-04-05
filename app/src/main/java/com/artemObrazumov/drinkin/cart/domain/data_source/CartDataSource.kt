package com.artemObrazumov.drinkin.cart.domain.data_source

import com.artemObrazumov.drinkin.cart.domain.models.ProductInCart
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import com.artemObrazumov.drinkin.product.domain.models.ProductDetails
import kotlinx.coroutines.flow.SharedFlow

interface CartDataSource {
    suspend fun addProductToCart(
        productDetails: ProductDetails,
        count: Int,
        selectedParameters: Map<String, Int>
    ): Result<Int, NetworkError>
    suspend fun incrementProductInCart(productId: Int)
    suspend fun decrementProductInCart(productId: Int)
    suspend fun removeProductFromCart(productId: Int)
    fun getProductsInCartFlow(): SharedFlow<List<ProductInCart>>
    suspend fun clearCart()
}
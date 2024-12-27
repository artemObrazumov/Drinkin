package com.artemObrazumov.drinkin.cart.domain.data_source

import com.artemObrazumov.drinkin.cart.domain.models.Order
import com.artemObrazumov.drinkin.core.domain.util.Error
import com.artemObrazumov.drinkin.core.domain.util.Result

interface OrderDataSource {

    /*
    * This method gets active order from the server in case the local one became invalid
    */
    suspend fun getOrderFromServer(): Result<Order, Error>
}
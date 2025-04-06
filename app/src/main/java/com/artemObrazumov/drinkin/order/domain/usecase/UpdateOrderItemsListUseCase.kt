package com.artemObrazumov.drinkin.order.domain.usecase

import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource

class UpdateOrderItemsListUseCase(
    private val orderDataSource: OrderDataSource
) {

    suspend operator fun invoke() {

    }
}
package com.artemObrazumov.drinkin.order.domain.utils

interface OrderTrackingServiceStarter {

    fun trackOrder(orderId: Int, orderNumber: Int)
}
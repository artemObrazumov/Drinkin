package com.artemObrazumov.drinkin.order.presentation.order_tracking

import android.content.Context
import android.content.Intent
import com.artemObrazumov.drinkin.order.domain.utils.OrderTrackingServiceStarter

class OrderTrackingServiceStarterImpl(
    private val context: Context
): OrderTrackingServiceStarter {

    override fun trackOrder(orderId: Int) {
        val intent = Intent(context, OrderTrackingService::class.java).apply {
            putExtra(OrderTrackingService.ORDER_ID_EXTRAS, orderId)
            putExtra(OrderTrackingService.ORDER_NUMBER_EXTRAS, 228)
        }
        context.startForegroundService(intent)
    }
}
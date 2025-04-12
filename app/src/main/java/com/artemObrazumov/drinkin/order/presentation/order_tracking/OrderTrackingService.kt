package com.artemObrazumov.drinkin.order.presentation.order_tracking

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.order.domain.usecase.GetOrderStatusFlowUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin

class OrderTrackingService : Service() {

    private val getOrderStatusFlowUseCase: GetOrderStatusFlowUseCase = getKoin().get()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var notificationManager: NotificationManager
    private val ordersList = TrackingOrdersList {
        stopSelf()
    }

    private var canPostNotifications = false

    override fun onCreate() {
        super.onCreate()
        checkPostNotificationsPermission()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        startForeground(FOREGROUND_ID, createServiceNotification())
    }

    private fun checkPostNotificationsPermission() {
        canPostNotifications = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            true
        } else {
            ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    @SuppressLint("MissingPermission")
    private fun postNotification(id: Int, notification: Notification) {
        if (canPostNotifications) {
            NotificationManagerCompat.from(this)
                .notify(id, notification)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val orderId = intent?.getIntExtra(ORDER_ID_EXTRAS, -1)
        if (orderId == null || orderId == -1)
            throw IllegalArgumentException("Order id provided incorrectly")

        val orderNumber = intent.getIntExtra(ORDER_NUMBER_EXTRAS, -1)
        if (orderNumber == -1)
            throw IllegalArgumentException("Order number provided incorrectly")

        startTrackingOrder(orderId, orderNumber)
        return START_STICKY
    }

    private fun startTrackingOrder(orderId: Int, orderNumber: Int) {
        postNotification(orderId, createOrderMakingNotification(orderNumber + orderId))
        ordersList.addOrder(orderId)
        scope.launch {
            getOrderStatusFlowUseCase.invoke(orderId).collect {
                stopTrackingOrder(orderId, orderNumber)
            }
        }
    }

    private fun stopTrackingOrder(orderId: Int, orderNumber: Int) {
        NotificationManagerCompat.from(this).cancel(orderId)
        postNotification(orderId, createOrderDoneNotification(orderNumber + orderId))
        ordersList.removeOrder(orderId)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun createOrderMakingNotification(orderNumber: Int): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(this.getString(R.string.order_is_making))
            .setContentText(this.getString(R.string.order_number, orderNumber))
            .setSmallIcon(R.drawable.coffee_icon)
            .setOngoing(true)
            .build()
    }

    private fun createOrderDoneNotification(orderNumber: Int): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(this.getString(R.string.order_is_done))
            .setContentText(this.getString(R.string.order_number, orderNumber))
            .setSmallIcon(R.drawable.coffee_icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(false)
            .build()
    }

    private fun createServiceNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(this.getString(R.string.tracking_order))
            .setSmallIcon(R.drawable.coffee_icon)
            .setOngoing(true)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    companion object {
        const val CHANNEL_ID = "OrderTrackerChannel"
        const val CHANNEL_NAME = "Order status"
        const val FOREGROUND_ID = 101

        const val ORDER_ID_EXTRAS = "order_id"
        const val ORDER_NUMBER_EXTRAS = "order_number"
    }

    class TrackingOrdersList(
        private val onEmpty: () -> Unit
    ) {

        private val ordersId = mutableListOf<Int>()

        fun addOrder(id: Int) {
            ordersId.add(id)
        }

        fun removeOrder(id: Int) {
            ordersId.remove(id)
            if (ordersId.isEmpty()) onEmpty.invoke()
        }
    }
}

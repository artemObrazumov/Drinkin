package com.artemObrazumov.drinkin.order.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.artemObrazumov.drinkin.cart.presentation.navigation.Cart
import com.artemObrazumov.drinkin.order.presentation.new_order.NewOrderScreen
import com.artemObrazumov.drinkin.order.presentation.new_order.NewOrderScreenViewModel
import com.artemObrazumov.drinkin.order.presentation.order_success.OrderSuccessScreen
import com.artemObrazumov.drinkin.core.presentation.components.menu.EmptyMenu
import com.artemObrazumov.drinkin.order.presentation.models.toOrderUi
import com.artemObrazumov.drinkin.order.presentation.order_details.OrderDetailsScreen
import com.artemObrazumov.drinkin.order.presentation.order_details.OrderDetailsScreenState
import com.artemObrazumov.drinkin.order.presentation.order_details.OrderDetailsScreenViewModel
import com.artemObrazumov.drinkin.order.presentation.orders.ORDERS
import com.artemObrazumov.drinkin.order.presentation.orders.OrdersScreen
import com.artemObrazumov.drinkin.order.presentation.orders.OrdersScreenViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersHolder

fun NavGraphBuilder.orderGraph(
    navController: NavController,
    orderScreenDestination: () -> @Serializable Any
) {
    composable<NewOrder>(
        enterTransition = { scaleIn(initialScale = 0.95f) + fadeIn() },
        exitTransition = { scaleOut(targetScale = 0.95f) + fadeOut() }
    ) {
        val viewModel: NewOrderScreenViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        NewOrderScreen(
            state = state,
            menu = { canReturn ->
                EmptyMenu(
                    title = "New order",
                    onBackButtonClicked = {
                        if (canReturn) {
                            navController.navigateUp()
                        }
                    }
                )
            },
            onPaymentClicked = { viewModel.startPayment() },
            onPaymentFinished = {
                navController.navigate(OrderSuccess) {
                    popUpTo<Cart>()
                }
            }
        )
    }

    composable<OrderSuccess>(
        enterTransition = { scaleIn(initialScale = 0.95f) + fadeIn() },
        exitTransition = { scaleOut(targetScale = 0.95f) + fadeOut() }
    ) {
        OrderSuccessScreen(
            menu = {
                EmptyMenu(
                    title = "",
                    color = Color.White,
                    onBackButtonClicked = { navController.navigateUp() }
                )
            },
            onOrdersClicked = { navController.navigate(orderScreenDestination()) {
                popUpTo<OrderSuccess> { inclusive = true }
            } }
        )
    }

    composable<Orders>(
        enterTransition = { scaleIn(initialScale = 0.95f) + fadeIn() },
        exitTransition = { scaleOut(targetScale = 0.95f) + fadeOut() }
    ) {
        val viewModel: OrdersScreenViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        OrdersScreen(
            state = state,
            menu = {
                EmptyMenu(
                    title = "Orders",
                    onBackButtonClicked = { navController.navigateUp() }
                )
            },
            onOrderClicked = { navController.navigate(OrderDetails(it)) }
        )
    }

    composable<OrderDetails>(
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) { backStackEntry ->
        val orderDetails = backStackEntry.toRoute<OrderDetails>()
        val viewModel: OrderDetailsScreenViewModel = koinViewModel(
            parameters = {
                ParametersHolder(
                    mutableListOf(orderDetails.id)
                )
            }
        )
        val state by viewModel.state.collectAsState()
        OrderDetailsScreen(
            state = state,
            menu = {
                EmptyMenu(
                    title = "Order Details",
                    onBackButtonClicked = { navController.navigateUp() }
                )
            },
        )
    }
}
package com.artemObrazumov.drinkin.cart.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artemObrazumov.drinkin.core.presentation.components.menu.MenuWithProfile
import com.artemObrazumov.drinkin.cart.presentation.cart.CartScreen
import com.artemObrazumov.drinkin.cart.presentation.cart.CartScreenViewModel
import com.artemObrazumov.drinkin.cart.presentation.new_order.NewOrderScreen
import com.artemObrazumov.drinkin.cart.presentation.new_order.NewOrderViewModel
import com.artemObrazumov.drinkin.core.presentation.components.menu.EmptyMenu
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.cartGraph(
    navController: NavController,
    addressDestination: () -> @Serializable Any,
    orderScreenDestination: () -> @Serializable Any
) {
    composable<Cart>(
        enterTransition = { scaleIn(initialScale = 0.95f) + fadeIn() },
        exitTransition = { scaleOut(targetScale = 0.95f) + fadeOut() }
    ) {
        val viewModel: CartScreenViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        CartScreen(
            state = state,
            menu = {
                MenuWithProfile(
                    title = "Cart",
                    onBackButtonClicked = { navController.navigateUp() },
                    onProfileIconClicked = { }
                )
            },
            onAddressClicked = { navController.navigate(addressDestination()) },
            onIncrementProduct = viewModel::incrementProduct,
            onDecrementProduct = viewModel::decrementProduct,
            onRemoveProduct = viewModel::removeProduct,
            onViewProductDetails = viewModel::showProductDetails,
            onHideDetails = viewModel::hideProductDetails,
            onMakeOrder = { navController.navigate(NewOrder) }
        )
    }

    composable<NewOrder>(
        enterTransition = { scaleIn(initialScale = 0.95f) + fadeIn() },
        exitTransition = { scaleOut(targetScale = 0.95f) + fadeOut() }
    ) {
        val viewModel: NewOrderViewModel = koinViewModel()
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

    composable<OrderSuccess> {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Order success!"
            )
        }
    }
}